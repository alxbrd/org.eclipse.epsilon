package org.eclipse.epsilon.eol.engine.test.acceptance;

import org.eclipse.epsilon.eol.engine.test.acceptance.eunit.EUnitRunner;
import org.eclipse.epsilon.eol.models.java.JavaObjectModel;
import org.eclipse.epsilon.eol.models.transactions.IModelTransactionSupport;
import org.junit.runner.RunWith;

@RunWith(EUnitRunner.class)
public class TransactionTests {
	
	class TransactionModel extends JavaObjectModel {
		
		protected boolean transactionStarted = false;
		protected boolean transactionRolledback = false;
		protected boolean transactionCommitted = false;
		
		@Override
		public IModelTransactionSupport getTransactionSupport() {
			return new IModelTransactionSupport() {
				
				@Override
				public void startTransaction() {
					transactionStarted = true;
				}
				
				@Override
				public void rollbackTransaction() {
					transactionRolledback = true;
				}
				
				@Override
				public void commitTransaction() {
					transactionCommitted = true;
				}
			};
		}
		
		public boolean isTransactionCommitted() {
			return transactionCommitted;
		}
		
		public boolean isTransactionRolledback() {
			return transactionRolledback;
		}
		
		public boolean isTransactionStarted() {
			return transactionStarted;
		}
		
	}
	
}
