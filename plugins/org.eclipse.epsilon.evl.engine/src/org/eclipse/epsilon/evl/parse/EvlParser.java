// $ANTLR 3.1b1 D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g 2010-10-19 13:28:26

package org.eclipse.epsilon.evl.parse;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 * -----------------------------------------------------------------------------
 * ANTLR 3 License
 * [The "BSD licence"]
 * Copyright (c) 2005-2008 Terence Parr
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
public class EvlParser extends org.eclipse.epsilon.commons.parse.EpsilonParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FLOAT", "DIGIT", "INT", "POINT", "POINT_POINT", "ARROW", "BOOLEAN", "EscapeSequence", "STRING", "StrangeNameLiteral", "Letter", "SpecialNameChar", "JavaIDDigit", "NAME", "WS", "COMMENT", "LINE_COMMENT", "Annotation", "FORMAL", "PARAMLIST", "ASSIGNMENT", "SPECIAL_ASSIGNMENT", "HELPERMETHOD", "StatementBlock", "FOR", "IF", "ELSE", "WHILE", "SWITCH", "CASE", "DEFAULT", "RETURN", "BREAK", "BREAKALL", "CONTINUE", "TRANSACTION", "COLLECTION", "ABORT", "CollectionType", "ModelElementType", "PARAMETERS", "NewExpression", "VAR", "NEW", "ANNOTATIONBLOCK", "EXECUTABLEANNOTATION", "DELETE", "THROW", "EXPRLIST", "EXPRRANGE", "NativeType", "MultiplicativeExpression", "OPERATOR", "FeatureCall", "EOLMODULE", "BLOCK", "FEATURECALL", "TYPE", "ENUMERATION_VALUE", "IMPORT", "MODELDECLARATION", "NAMESPACE", "ALIAS", "DRIVER", "MODELDECLARATIONPARAMETERS", "MODELDECLARATIONPARAMETER", "ITEMSELECTOR", "PRE", "POST", "EXTENDS", "GUARD", "CONSTRAINT", "CRITIQUE", "CONTEXT", "FIX", "CHECK", "DO", "TITLE", "MESSAGE", "EVLMODULE", "'model'", "';'", "':'", "','", "'alias'", "'driver'", "'{'", "'}'", "'='", "'operation'", "'function'", "'('", "')'", "'import'", "'$'", "'!'", "'::'", "'#'", "'Native'", "'Collection'", "'Sequence'", "'List'", "'Bag'", "'Set'", "'OrderedSet'", "'for'", "'in'", "'if'", "'switch'", "'case'", "'default'", "'else'", "'while'", "'return'", "'throw'", "'delete'", "'break'", "'breakAll'", "'continue'", "'abort'", "'transaction'", "':='", "'::='", "'or'", "'and'", "'xor'", "'implies'", "'=='", "'>'", "'<'", "'>='", "'<='", "'<>'", "'+'", "'-'", "'*'", "'/'", "'not'", "'['", "']'", "'|'", "'new'", "'var'", "'pre'", "'post'", "'guard'", "'extends'", "'context'", "'constraint'", "'critique'", "'check'", "'message'", "'fix'", "'title'", "'do'"
    };
    public static final int FIX=78;
    public static final int WHILE=31;
    public static final int StatementBlock=27;
    public static final int T__158=158;
    public static final int StrangeNameLiteral=13;
    public static final int CASE=33;
    public static final int NEW=47;
    public static final int DO=80;
    public static final int FeatureCall=57;
    public static final int EOF=-1;
    public static final int BREAK=36;
    public static final int TYPE=61;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int IMPORT=63;
    public static final int NAME=17;
    public static final int T__92=92;
    public static final int T__148=148;
    public static final int T__90=90;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int RETURN=35;
    public static final int NewExpression=45;
    public static final int VAR=46;
    public static final int ANNOTATIONBLOCK=48;
    public static final int NativeType=54;
    public static final int ABORT=41;
    public static final int COMMENT=19;
    public static final int T__154=154;
    public static final int T__155=155;
    public static final int T__156=156;
    public static final int T__99=99;
    public static final int T__157=157;
    public static final int ITEMSELECTOR=70;
    public static final int T__98=98;
    public static final int T__150=150;
    public static final int T__97=97;
    public static final int T__151=151;
    public static final int MultiplicativeExpression=55;
    public static final int T__96=96;
    public static final int T__152=152;
    public static final int T__95=95;
    public static final int T__153=153;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int LINE_COMMENT=20;
    public static final int BREAKALL=37;
    public static final int TRANSACTION=39;
    public static final int SWITCH=32;
    public static final int DRIVER=67;
    public static final int ELSE=30;
    public static final int EOLMODULE=58;
    public static final int MODELDECLARATION=64;
    public static final int PARAMLIST=23;
    public static final int INT=6;
    public static final int DELETE=50;
    public static final int T__85=85;
    public static final int T__141=141;
    public static final int T__84=84;
    public static final int T__142=142;
    public static final int T__87=87;
    public static final int HELPERMETHOD=26;
    public static final int T__86=86;
    public static final int T__140=140;
    public static final int T__89=89;
    public static final int T__145=145;
    public static final int NAMESPACE=65;
    public static final int T__88=88;
    public static final int T__146=146;
    public static final int CollectionType=42;
    public static final int T__143=143;
    public static final int T__144=144;
    public static final int T__126=126;
    public static final int T__125=125;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int WS=18;
    public static final int T__129=129;
    public static final int EVLMODULE=83;
    public static final int ALIAS=66;
    public static final int JavaIDDigit=16;
    public static final int CHECK=79;
    public static final int GUARD=74;
    public static final int Annotation=21;
    public static final int CONSTRAINT=75;
    public static final int T__130=130;
    public static final int T__131=131;
    public static final int EscapeSequence=11;
    public static final int Letter=14;
    public static final int THROW=51;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int T__134=134;
    public static final int T__135=135;
    public static final int SPECIAL_ASSIGNMENT=25;
    public static final int MODELDECLARATIONPARAMETER=69;
    public static final int PARAMETERS=44;
    public static final int POINT=7;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__124=124;
    public static final int T__123=123;
    public static final int FOR=28;
    public static final int T__122=122;
    public static final int ENUMERATION_VALUE=62;
    public static final int T__121=121;
    public static final int PRE=71;
    public static final int T__120=120;
    public static final int FLOAT=4;
    public static final int EXECUTABLEANNOTATION=49;
    public static final int CONTEXT=77;
    public static final int IF=29;
    public static final int ModelElementType=43;
    public static final int BOOLEAN=10;
    public static final int CONTINUE=38;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int MESSAGE=82;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int COLLECTION=40;
    public static final int DIGIT=5;
    public static final int CRITIQUE=76;
    public static final int EXPRRANGE=53;
    public static final int OPERATOR=56;
    public static final int EXPRLIST=52;
    public static final int DEFAULT=34;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int POINT_POINT=8;
    public static final int SpecialNameChar=15;
    public static final int MODELDECLARATIONPARAMETERS=68;
    public static final int BLOCK=59;
    public static final int FEATURECALL=60;
    public static final int FORMAL=22;
    public static final int POST=72;
    public static final int ARROW=9;
    public static final int ASSIGNMENT=24;
    public static final int EXTENDS=73;
    public static final int STRING=12;
    public static final int TITLE=81;

    // delegates
    public Evl_EolParserRules gEolParserRules;
    public Evl_ErlParserRules gErlParserRules;
    public Evl_EvlParserRules gEvlParserRules;
    // delegators


        public EvlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public EvlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gEolParserRules = new Evl_EolParserRules(input, state, this);
            gErlParserRules = new Evl_ErlParserRules(input, state, this);
            gEvlParserRules = new Evl_EvlParserRules(input, state, this);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EvlParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g"; }


    public static class evlModule_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start evlModule
    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:94:1: evlModule : ( importStatement )* ( evlModuleContent )* EOF -> ^( EVLMODULE ( importStatement )* ( evlModuleContent )* ) ;
    public final EvlParser.evlModule_return evlModule() throws RecognitionException {
        EvlParser.evlModule_return retval = new EvlParser.evlModule_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token EOF3=null;
        Evl_EolParserRules.importStatement_return importStatement1 = null;

        EvlParser.evlModuleContent_return evlModuleContent2 = null;


        CommonTree EOF3_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_importStatement=new RewriteRuleSubtreeStream(adaptor,"rule importStatement");
        RewriteRuleSubtreeStream stream_evlModuleContent=new RewriteRuleSubtreeStream(adaptor,"rule evlModuleContent");
        try {
            // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:95:2: ( ( importStatement )* ( evlModuleContent )* EOF -> ^( EVLMODULE ( importStatement )* ( evlModuleContent )* ) )
            // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:95:4: ( importStatement )* ( evlModuleContent )* EOF
            {
            // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:95:4: ( importStatement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==97) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:0:0: importStatement
            	    {
            	    pushFollow(FOLLOW_importStatement_in_evlModule76);
            	    importStatement1=importStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_importStatement.add(importStatement1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:95:21: ( evlModuleContent )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==Annotation||(LA2_0>=93 && LA2_0<=94)||LA2_0==98||(LA2_0>=147 && LA2_0<=148)||LA2_0==151) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:95:22: evlModuleContent
            	    {
            	    pushFollow(FOLLOW_evlModuleContent_in_evlModule80);
            	    evlModuleContent2=evlModuleContent();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_evlModuleContent.add(evlModuleContent2.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_evlModule84); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF3);



            // AST REWRITE
            // elements: evlModuleContent, importStatement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 96:2: -> ^( EVLMODULE ( importStatement )* ( evlModuleContent )* )
            {
                // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:96:5: ^( EVLMODULE ( importStatement )* ( evlModuleContent )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(EVLMODULE, "EVLMODULE"), root_1);

                // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:96:17: ( importStatement )*
                while ( stream_importStatement.hasNext() ) {
                    adaptor.addChild(root_1, stream_importStatement.nextTree());

                }
                stream_importStatement.reset();
                // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:96:34: ( evlModuleContent )*
                while ( stream_evlModuleContent.hasNext() ) {
                    adaptor.addChild(root_1, stream_evlModuleContent.nextTree());

                }
                stream_evlModuleContent.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end evlModule

    public static class evlModuleContent_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start evlModuleContent
    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:99:1: evlModuleContent : ( pre | annotationBlock | context | operationDeclaration | post );
    public final EvlParser.evlModuleContent_return evlModuleContent() throws RecognitionException {
        EvlParser.evlModuleContent_return retval = new EvlParser.evlModuleContent_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Evl_ErlParserRules.pre_return pre4 = null;

        Evl_EolParserRules.annotationBlock_return annotationBlock5 = null;

        Evl_EvlParserRules.context_return context6 = null;

        Evl_EolParserRules.operationDeclaration_return operationDeclaration7 = null;

        Evl_ErlParserRules.post_return post8 = null;



        try {
            // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:100:2: ( pre | annotationBlock | context | operationDeclaration | post )
            int alt3=5;
            switch ( input.LA(1) ) {
            case 147:
                {
                alt3=1;
                }
                break;
            case Annotation:
            case 98:
                {
                alt3=2;
                }
                break;
            case 151:
                {
                alt3=3;
                }
                break;
            case 93:
            case 94:
                {
                alt3=4;
                }
                break;
            case 148:
                {
                alt3=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:100:4: pre
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_pre_in_evlModuleContent108);
                    pre4=pre();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pre4.getTree());

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:100:10: annotationBlock
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_annotationBlock_in_evlModuleContent112);
                    annotationBlock5=annotationBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, annotationBlock5.getTree());

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:100:28: context
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_context_in_evlModuleContent116);
                    context6=context();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, context6.getTree());

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:100:38: operationDeclaration
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_operationDeclaration_in_evlModuleContent120);
                    operationDeclaration7=operationDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operationDeclaration7.getTree());

                    }
                    break;
                case 5 :
                    // D:\\Projects\\Eclipse\\3.5.1\\workspace\\org.eclipse.epsilon.evl.engine\\src\\org\\eclipse\\epsilon\\evl\\parse\\Evl.g:100:61: post
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_post_in_evlModuleContent124);
                    post8=post();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, post8.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end evlModuleContent

    // Delegated rules
    public Evl_EvlParserRules.context_return context() throws RecognitionException { return gEvlParserRules.context(); }
    public Evl_EolParserRules.switchStatement_return switchStatement() throws RecognitionException { return gEolParserRules.switchStatement(); }
    public Evl_EolParserRules.collectionType_return collectionType() throws RecognitionException { return gEolParserRules.collectionType(); }
    public Evl_EvlParserRules.message_return message() throws RecognitionException { return gEvlParserRules.message(); }
    public Evl_ErlParserRules.pre_return pre() throws RecognitionException { return gErlParserRules.pre(); }
    public Evl_EolParserRules.modelDeclarationParameter_return modelDeclarationParameter() throws RecognitionException { return gEolParserRules.modelDeclarationParameter(); }
    public Evl_EolParserRules.statementBlock_return statementBlock() throws RecognitionException { return gEolParserRules.statementBlock(); }
    public Evl_EolParserRules.unaryExpression_return unaryExpression() throws RecognitionException { return gEolParserRules.unaryExpression(); }
    public Evl_EolParserRules.literal_return literal() throws RecognitionException { return gEolParserRules.literal(); }
    public Evl_EolParserRules.elseStatement_return elseStatement() throws RecognitionException { return gEolParserRules.elseStatement(); }
    public Evl_EolParserRules.modelDeclarationParameters_return modelDeclarationParameters() throws RecognitionException { return gEolParserRules.modelDeclarationParameters(); }
    public Evl_EolParserRules.additiveExpression_return additiveExpression() throws RecognitionException { return gEolParserRules.additiveExpression(); }
    public Evl_EolParserRules.breakAllStatement_return breakAllStatement() throws RecognitionException { return gEolParserRules.breakAllStatement(); }
    public Evl_EolParserRules.caseStatement_return caseStatement() throws RecognitionException { return gEolParserRules.caseStatement(); }
    public Evl_EolParserRules.postfixExpression_return postfixExpression() throws RecognitionException { return gEolParserRules.postfixExpression(); }
    public Evl_EolParserRules.deleteStatement_return deleteStatement() throws RecognitionException { return gEolParserRules.deleteStatement(); }
    public Evl_EolParserRules.expressionRange_return expressionRange() throws RecognitionException { return gEolParserRules.expressionRange(); }
    public Evl_EolParserRules.pathName_return pathName() throws RecognitionException { return gEolParserRules.pathName(); }
    public Evl_EolParserRules.annotation_return annotation() throws RecognitionException { return gEolParserRules.annotation(); }
    public Evl_EvlParserRules.check_return check() throws RecognitionException { return gEvlParserRules.check(); }
    public Evl_EolParserRules.modelDeclaration_return modelDeclaration() throws RecognitionException { return gEolParserRules.modelDeclaration(); }
    public Evl_EolParserRules.primitiveExpression_return primitiveExpression() throws RecognitionException { return gEolParserRules.primitiveExpression(); }
    public Evl_EvlParserRules.fixBody_return fixBody() throws RecognitionException { return gEvlParserRules.fixBody(); }
    public Evl_EolParserRules.transactionStatement_return transactionStatement() throws RecognitionException { return gEolParserRules.transactionStatement(); }
    public Evl_EolParserRules.statement_return statement() throws RecognitionException { return gEolParserRules.statement(); }
    public Evl_EolParserRules.block_return block() throws RecognitionException { return gEolParserRules.block(); }
    public Evl_EolParserRules.modelAlias_return modelAlias() throws RecognitionException { return gEolParserRules.modelAlias(); }
    public Evl_EvlParserRules.constraint_return constraint() throws RecognitionException { return gEvlParserRules.constraint(); }
    public Evl_EolParserRules.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gEolParserRules.multiplicativeExpression(); }
    public Evl_EolParserRules.assignmentStatement_return assignmentStatement() throws RecognitionException { return gEolParserRules.assignmentStatement(); }
    public Evl_EolParserRules.throwStatement_return throwStatement() throws RecognitionException { return gEolParserRules.throwStatement(); }
    public Evl_EolParserRules.breakStatement_return breakStatement() throws RecognitionException { return gEolParserRules.breakStatement(); }
    public Evl_EolParserRules.abortStatement_return abortStatement() throws RecognitionException { return gEolParserRules.abortStatement(); }
    public Evl_EolParserRules.forStatement_return forStatement() throws RecognitionException { return gEolParserRules.forStatement(); }
    public Evl_EolParserRules.operationDeclarationOrAnnotationBlock_return operationDeclarationOrAnnotationBlock() throws RecognitionException { return gEolParserRules.operationDeclarationOrAnnotationBlock(); }
    public Evl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock() throws RecognitionException { return gEolParserRules.expressionOrStatementBlock(); }
    public Evl_EolParserRules.declarativeFeatureCall_return declarativeFeatureCall() throws RecognitionException { return gEolParserRules.declarativeFeatureCall(); }
    public Evl_EolParserRules.annotationBlock_return annotationBlock() throws RecognitionException { return gEolParserRules.annotationBlock(); }
    public Evl_EolParserRules.continueStatement_return continueStatement() throws RecognitionException { return gEolParserRules.continueStatement(); }
    public Evl_EolParserRules.expressionStatement_return expressionStatement() throws RecognitionException { return gEolParserRules.expressionStatement(); }
    public Evl_EolParserRules.statementB_return statementB() throws RecognitionException { return gEolParserRules.statementB(); }
    public Evl_EvlParserRules.critique_return critique() throws RecognitionException { return gEvlParserRules.critique(); }
    public Evl_EolParserRules.modelNamespace_return modelNamespace() throws RecognitionException { return gEolParserRules.modelNamespace(); }
    public Evl_EolParserRules.typeName_return typeName() throws RecognitionException { return gEolParserRules.typeName(); }
    public Evl_EolParserRules.logicalExpression_return logicalExpression() throws RecognitionException { return gEolParserRules.logicalExpression(); }
    public Evl_EolParserRules.newExpression_return newExpression() throws RecognitionException { return gEolParserRules.newExpression(); }
    public Evl_ErlParserRules.extendz_return extendz() throws RecognitionException { return gErlParserRules.extendz(); }
    public Evl_EvlParserRules.contextContent_return contextContent() throws RecognitionException { return gEvlParserRules.contextContent(); }
    public Evl_EvlParserRules.fix_return fix() throws RecognitionException { return gEvlParserRules.fix(); }
    public Evl_EolParserRules.nativeType_return nativeType() throws RecognitionException { return gEolParserRules.nativeType(); }
    public Evl_EolParserRules.defaultStatement_return defaultStatement() throws RecognitionException { return gEolParserRules.defaultStatement(); }
    public Evl_EolParserRules.modelDriver_return modelDriver() throws RecognitionException { return gEolParserRules.modelDriver(); }
    public Evl_EolParserRules.itemSelectorExpression_return itemSelectorExpression() throws RecognitionException { return gEolParserRules.itemSelectorExpression(); }
    public Evl_ErlParserRules.guard_return guard() throws RecognitionException { return gErlParserRules.guard(); }
    public Evl_EolParserRules.ifStatement_return ifStatement() throws RecognitionException { return gEolParserRules.ifStatement(); }
    public Evl_EolParserRules.variableDeclarationExpression_return variableDeclarationExpression() throws RecognitionException { return gEolParserRules.variableDeclarationExpression(); }
    public Evl_EolParserRules.litteralCollection_return litteralCollection() throws RecognitionException { return gEolParserRules.litteralCollection(); }
    public Evl_EolParserRules.expressionListOrRange_return expressionListOrRange() throws RecognitionException { return gEolParserRules.expressionListOrRange(); }
    public Evl_EolParserRules.whileStatement_return whileStatement() throws RecognitionException { return gEolParserRules.whileStatement(); }
    public Evl_EolParserRules.importStatement_return importStatement() throws RecognitionException { return gEolParserRules.importStatement(); }
    public Evl_EolParserRules.modelElementType_return modelElementType() throws RecognitionException { return gEolParserRules.modelElementType(); }
    public Evl_EolParserRules.simpleFeatureCall_return simpleFeatureCall() throws RecognitionException { return gEolParserRules.simpleFeatureCall(); }
    public Evl_EolParserRules.parameterList_return parameterList() throws RecognitionException { return gEolParserRules.parameterList(); }
    public Evl_EolParserRules.featureCall_return featureCall() throws RecognitionException { return gEolParserRules.featureCall(); }
    public Evl_EolParserRules.executableAnnotation_return executableAnnotation() throws RecognitionException { return gEolParserRules.executableAnnotation(); }
    public Evl_EolParserRules.expressionList_return expressionList() throws RecognitionException { return gEolParserRules.expressionList(); }
    public Evl_EolParserRules.formalParameter_return formalParameter() throws RecognitionException { return gEolParserRules.formalParameter(); }
    public Evl_EolParserRules.relationalExpression_return relationalExpression() throws RecognitionException { return gEolParserRules.relationalExpression(); }
    public Evl_EolParserRules.formalParameterList_return formalParameterList() throws RecognitionException { return gEolParserRules.formalParameterList(); }
    public Evl_ErlParserRules.post_return post() throws RecognitionException { return gErlParserRules.post(); }
    public Evl_EolParserRules.operationDeclaration_return operationDeclaration() throws RecognitionException { return gEolParserRules.operationDeclaration(); }
    public Evl_EvlParserRules.title_return title() throws RecognitionException { return gEvlParserRules.title(); }
    public Evl_EolParserRules.returnStatement_return returnStatement() throws RecognitionException { return gEolParserRules.returnStatement(); }
    public Evl_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock() throws RecognitionException { return gEolParserRules.statementOrStatementBlock(); }
    public Evl_EolParserRules.statementA_return statementA() throws RecognitionException { return gEolParserRules.statementA(); }


 

    public static final BitSet FOLLOW_importStatement_in_evlModule76 = new BitSet(new long[]{0x0000000000200000L,0x0000000660000000L,0x0000000000980000L});
    public static final BitSet FOLLOW_evlModuleContent_in_evlModule80 = new BitSet(new long[]{0x0000000000200000L,0x0000000460000000L,0x0000000000980000L});
    public static final BitSet FOLLOW_EOF_in_evlModule84 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_in_evlModuleContent108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationBlock_in_evlModuleContent112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_context_in_evlModuleContent116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationDeclaration_in_evlModuleContent120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_post_in_evlModuleContent124 = new BitSet(new long[]{0x0000000000000002L});

}