package org.eclipse.epsilon.eol.dom;

import java.util.Collection;

import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.contributors.IterableOperationContributor;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinterManager;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.EolObjectComparator;
import org.eclipse.epsilon.eol.types.NumberUtil;

public class OperatorExpression extends Expression {

	private IEolContext context;
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException{
		this.context = context;
		String operator = getText();
		AST operand1Ast = getFirstChild();
		AST operand2Ast = operand1Ast.getNextSibling();
		Object operand1 = null; 
		Object operand2 = null;
		
		
		if (isBooleanOperator(operator)){
			if (operator.compareTo("and") == 0){
				return and(operand1Ast, operand2Ast);
			}
			else if (operator.compareTo("or") == 0){
				return or(operand1Ast, operand2Ast);
			}
			else if (operator.compareTo("xor") == 0){
				return xor(operand1Ast, operand2Ast);
			}
			else if (operator.compareTo("implies") == 0){
				return implies(operand1Ast, operand2Ast);
			}
			else if (operator.compareTo("not") == 0){
				return not(operand1Ast);
			}
		}
		else {
			operand1 = context.getExecutorFactory().executeAST(operand1Ast, context);
			
			if (operand2Ast != null){
				operand2 = context.getExecutorFactory().executeAST(operand2Ast, context);
			}
			
			if (operator.compareTo("+") == 0){
				return add(operand1, operand2);
			}
			else if (operator.compareTo("-") == 0){
				if (operand2 != null){
					return subtract(operand1, operand2);
				}
				else {
					return negative(operand1);
				}
			}
			else if (operator.compareTo("*") == 0){
				return multiply(operand1, operand2);
			}
			else if (operator.compareTo("/") == 0){
				return divide(operand1, operand2);
			}
			else if (operator.compareTo("<") == 0){
				return lessThan(operand1, operand2);
			}
			else if (operator.compareTo(">") == 0){
				return greaterThan(operand1, operand2);
			}
			else if (operator.compareTo("=") == 0){
				return equals(operand1, operand2);
			}
			else if (operator.compareTo("==") == 0){
				return equals(operand1, operand2);
			}
			else if (operator.compareTo("<>") == 0){
				return !equals(operand1, operand2);
			}
			else if (operator.compareTo(">=") == 0){
				return greaterEqualThan(operand1, operand2);
			}
			else if (operator.compareTo("<=") == 0){
				return lessEqualThan(operand1, operand2);
			}
		}
		return null;
	}
	
	private Object negative(Object o) {
		if (o instanceof Number){
			return NumberUtil.negative((Number) o);
		}
		return o;
	}
	
	
	private Object add(Object o1, Object o2){
		if (o1 instanceof Number && o2 instanceof Number){
			return NumberUtil.add((Number) o1, (Number) o2);
		}
		else if (o1 instanceof Collection && o2 instanceof Collection){
			return EolCollectionType.join((Collection) o1, (Collection)o2);
		}
		
		// If we can do nothing more, we concat their string
		// representations
		
		PrettyPrinterManager prettyPrinterManager = context.getPrettyPrinterManager();	
		return prettyPrinterManager.print(o1) + prettyPrinterManager.print(o2);

	}

	private Object subtract(Object o1, Object o2){
		if (o1 instanceof Number && o2 instanceof Number){
			return NumberUtil.subtract((Number) o1, (Number) o2);
		}
		else if (o1 instanceof Collection && o2 instanceof Collection){
			return new IterableOperationContributor((Collection<?>) o1).excludingAll((Collection<?>) o2);
		}
		
		return null;
	}

	private Object multiply(Object o1, Object o2){
		if (o1 instanceof Number && o2 instanceof Number){
			return NumberUtil.multiply((Number) o1, (Number) o2);
		}
		return new Integer(0);
	}

	private Object divide(Object o1, Object o2) throws EolRuntimeException {
		if (o1 instanceof Number && o2 instanceof Number){
			return NumberUtil.divide((Number) o1, (Number) o2);
		}
		throw new EolRuntimeException("Cannot divide " + context.getPrettyPrinterManager().toString(o1) + " by " + context.getPrettyPrinterManager().toString(o2));
	}
	
	public boolean greaterThan(Object o1, Object o2){
		if (o1 instanceof Number && o2 instanceof Number){
			return NumberUtil.greaterThan((Number) o1, (Number) o2);
		}
		return false;
	}
	
	public boolean lessThan(Object o1, Object o2){
		if (o1 instanceof Number && o2 instanceof Number){
			return NumberUtil.lessThan((Number) o1, (Number) o2);
		}
		return false;
	}
	
	public boolean equals(Object o1, Object o2){
		
		return EolObjectComparator.equals(o1, o2);
		
	}
	
	public boolean greaterEqualThan(Object o1, Object o2){
		return greaterThan(o1,o2) || equals(o1,o2);
	}
	
	public boolean lessEqualThan(Object o1, Object o2){
		return lessThan(o1,o2) || equals(o1,o2);
	}

	public boolean and(AST operand1Ast, AST operand2Ast) throws EolRuntimeException{
		
		Object o1 = context.getExecutorFactory().executeAST(operand1Ast,context);
		
		if (o1 instanceof Boolean) {
			Boolean b1 = (Boolean) o1;
			if (b1.booleanValue() == false) {
				return false;
			}
			else {
				Object o2 = context.getExecutorFactory().executeAST(operand2Ast,context);
				if (o2 instanceof Boolean){
					return (Boolean) o2;
				}
				else {
					throw new EolRuntimeException("Operator 'and' applies only to operands of type Boolean", this);
				}
			}
		}
		else {
			throw new EolRuntimeException("Operator 'and' applies only to operands of type Boolean", this);
		}
		
	}

	public boolean or(AST operand1Ast, AST operand2Ast) throws EolRuntimeException{
		
		Object o1 = context.getExecutorFactory().executeAST(operand1Ast,context);
		
		if (o1 instanceof Boolean) {
			Boolean b1 = (Boolean) o1;
			if (b1.booleanValue() == true) {
				return true;
			}
			else {
				Object o2 = context.getExecutorFactory().executeAST(operand2Ast,context);
				if (o2 instanceof Boolean){
					return (Boolean) o2;
				}
				else {
					throw new EolRuntimeException("Operator 'or' applies only to operands of type Boolean", this);
				}
			}
		}
		else {
			throw new EolRuntimeException("Operator 'or' applies only to operands of type Boolean", this);
		}
		
	}

	public boolean implies(AST operand1Ast, AST operand2Ast) throws EolRuntimeException{
		
		Object o1 = context.getExecutorFactory().executeAST(operand1Ast,context);
		
		if (o1 instanceof Boolean) {
			Boolean b1 = (Boolean) o1;
			if (b1.booleanValue() == false) {
				return true;
			}
			else {
				Object o2 = context.getExecutorFactory().executeAST(operand2Ast,context);
				if (o2 instanceof Boolean){
					return ((Boolean) o2) && ((Boolean) o1);
				}
				else {
					throw new EolRuntimeException("Operator 'implies' applies only to operands of type Boolean", this);
				}
			}
		}
		else {
			throw new EolRuntimeException("Operator 'implies' applies only to operands of type Boolean", this);
		}
		
	}
	
	public boolean xor(AST operand1Ast, AST operand2Ast) throws EolRuntimeException{
		Object o1 = context.getExecutorFactory().executeAST(operand1Ast,context);
		Object o2 = context.getExecutorFactory().executeAST(operand2Ast,context);
		if (o1 instanceof Boolean && o2 instanceof Boolean){
			return ((Boolean) o1) ^ ((Boolean) o2);
		} else {
			throw new EolRuntimeException("Operator 'xor' applies only to Booleans", this);
		}
	}
	
	public Boolean not(AST operand1Ast) throws EolRuntimeException{
		Object o1 = context.getExecutorFactory().executeAST(operand1Ast,context);
		if (o1 instanceof Boolean){
			return !((Boolean) o1);
		} else {
			throw new EolRuntimeException("Operator 'not' applies only to Booleans", this);
		}
	}
	
	public boolean isBooleanOperator(String operator){
		return operator.equals("and") ||
			operator.equals("or") ||
			operator.equals("xor") ||
			operator.equals("not") ||
			operator.equals("implies");
	}
	
}
