// $ANTLR 3.1b1 /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g 2015-10-02 23:15:25

package org.eclipse.epsilon.edl.parse;


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
public class EdlParser extends org.eclipse.epsilon.common.parse.EpsilonParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FLOAT", "DIGIT", "EXPONENT", "FLOAT_TYPE_SUFFIX", "INT", "POINT", "POINT_POINT", "ARROW", "BOOLEAN", "EscapeSequence", "STRING", "StrangeNameLiteral", "Letter", "SpecialNameChar", "JavaIDDigit", "NAME", "WS", "COMMENT", "LINE_COMMENT", "Annotation", "FORMAL", "PARAMLIST", "ASSIGNMENT", "SPECIAL_ASSIGNMENT", "HELPERMETHOD", "StatementBlock", "FOR", "IF", "ELSE", "WHILE", "SWITCH", "CASE", "DEFAULT", "RETURN", "BREAK", "BREAKALL", "CONTINUE", "TRANSACTION", "COLLECTION", "ABORT", "CollectionType", "ModelElementType", "PARAMETERS", "NewExpression", "VAR", "NEW", "ANNOTATIONBLOCK", "EXECUTABLEANNOTATION", "DELETE", "THROW", "EXPRLIST", "EXPRRANGE", "NativeType", "MultiplicativeExpression", "OPERATOR", "EXPRESSIONINBRACKETS", "FeatureCall", "EOLMODULE", "BLOCK", "FEATURECALL", "TYPE", "ENUMERATION_VALUE", "IMPORT", "MODELDECLARATION", "NAMESPACE", "ALIAS", "DRIVER", "MODELDECLARATIONPARAMETERS", "MODELDECLARATIONPARAMETER", "ITEMSELECTOR", "MAP", "KEYVAL", "KEYVALLIST", "PRE", "POST", "EXTENDS", "GUARD", "PROCESS", "EDLMODULE", "'model'", "';'", "'alias'", "','", "'driver'", "'{'", "'}'", "'='", "'operation'", "'function'", "'('", "')'", "':'", "'import'", "'$'", "'!'", "'#'", "'::'", "'Native'", "'Collection'", "'Sequence'", "'List'", "'Bag'", "'Set'", "'OrderedSet'", "'Map'", "'<'", "'>'", "'for'", "'in'", "'if'", "'switch'", "'case'", "'default'", "'else'", "'while'", "'return'", "'throw'", "'delete'", "'break'", "'breakAll'", "'continue'", "'abort'", "'transaction'", "':='", "'+='", "'-='", "'*='", "'/='", "'::='", "'or'", "'and'", "'xor'", "'implies'", "'=='", "'>='", "'<='", "'<>'", "'+'", "'-'", "'*'", "'/'", "'not'", "'++'", "'['", "']'", "'|'", "'new'", "'var'", "'ext'", "'pre'", "'post'", "'guard'", "'extends'", "'process'"
    };
    public static final int EXPONENT=6;
    public static final int WHILE=33;
    public static final int StatementBlock=29;
    public static final int StrangeNameLiteral=15;
    public static final int CASE=35;
    public static final int NEW=49;
    public static final int FeatureCall=60;
    public static final int EOF=-1;
    public static final int BREAK=38;
    public static final int KEYVALLIST=76;
    public static final int TYPE=64;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int IMPORT=66;
    public static final int NAME=19;
    public static final int T__92=92;
    public static final int T__148=148;
    public static final int T__90=90;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int RETURN=37;
    public static final int NewExpression=47;
    public static final int VAR=48;
    public static final int ANNOTATIONBLOCK=50;
    public static final int NativeType=56;
    public static final int ABORT=43;
    public static final int COMMENT=21;
    public static final int T__154=154;
    public static final int T__155=155;
    public static final int T__156=156;
    public static final int T__99=99;
    public static final int T__157=157;
    public static final int ITEMSELECTOR=73;
    public static final int T__98=98;
    public static final int T__150=150;
    public static final int T__97=97;
    public static final int T__151=151;
    public static final int MultiplicativeExpression=57;
    public static final int T__96=96;
    public static final int T__152=152;
    public static final int T__95=95;
    public static final int T__153=153;
    public static final int FLOAT_TYPE_SUFFIX=7;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int T__83=83;
    public static final int LINE_COMMENT=22;
    public static final int BREAKALL=39;
    public static final int EDLMODULE=82;
    public static final int TRANSACTION=41;
    public static final int SWITCH=34;
    public static final int DRIVER=70;
    public static final int ELSE=32;
    public static final int EOLMODULE=61;
    public static final int MODELDECLARATION=67;
    public static final int PARAMLIST=25;
    public static final int INT=8;
    public static final int DELETE=52;
    public static final int T__85=85;
    public static final int T__141=141;
    public static final int T__84=84;
    public static final int T__142=142;
    public static final int T__87=87;
    public static final int HELPERMETHOD=28;
    public static final int T__86=86;
    public static final int T__140=140;
    public static final int T__89=89;
    public static final int T__145=145;
    public static final int NAMESPACE=68;
    public static final int T__88=88;
    public static final int T__146=146;
    public static final int CollectionType=44;
    public static final int T__143=143;
    public static final int T__144=144;
    public static final int T__126=126;
    public static final int T__125=125;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int WS=20;
    public static final int T__129=129;
    public static final int ALIAS=69;
    public static final int JavaIDDigit=18;
    public static final int GUARD=80;
    public static final int Annotation=23;
    public static final int T__130=130;
    public static final int Letter=16;
    public static final int T__131=131;
    public static final int EscapeSequence=13;
    public static final int THROW=53;
    public static final int T__132=132;
    public static final int T__133=133;
    public static final int T__134=134;
    public static final int T__135=135;
    public static final int SPECIAL_ASSIGNMENT=27;
    public static final int MODELDECLARATIONPARAMETER=72;
    public static final int KEYVAL=75;
    public static final int PARAMETERS=46;
    public static final int POINT=9;
    public static final int T__118=118;
    public static final int T__119=119;
    public static final int T__116=116;
    public static final int T__117=117;
    public static final int T__114=114;
    public static final int T__115=115;
    public static final int T__124=124;
    public static final int T__123=123;
    public static final int FOR=30;
    public static final int T__122=122;
    public static final int ENUMERATION_VALUE=65;
    public static final int T__121=121;
    public static final int PRE=77;
    public static final int T__120=120;
    public static final int FLOAT=4;
    public static final int EXECUTABLEANNOTATION=51;
    public static final int IF=31;
    public static final int ModelElementType=45;
    public static final int BOOLEAN=12;
    public static final int CONTINUE=40;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int T__109=109;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__112=112;
    public static final int COLLECTION=42;
    public static final int DIGIT=5;
    public static final int EXPRRANGE=55;
    public static final int OPERATOR=58;
    public static final int EXPRLIST=54;
    public static final int DEFAULT=36;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int POINT_POINT=10;
    public static final int SpecialNameChar=17;
    public static final int MODELDECLARATIONPARAMETERS=71;
    public static final int BLOCK=62;
    public static final int MAP=74;
    public static final int FEATURECALL=63;
    public static final int FORMAL=24;
    public static final int POST=78;
    public static final int ARROW=11;
    public static final int EXPRESSIONINBRACKETS=59;
    public static final int ASSIGNMENT=26;
    public static final int PROCESS=81;
    public static final int EXTENDS=79;
    public static final int STRING=14;

    // delegates
    public Edl_EolParserRules gEolParserRules;
    public Edl_ErlParserRules gErlParserRules;
    public Edl_EdlParserRules gEdlParserRules;
    // delegators


        public EdlParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public EdlParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gEolParserRules = new Edl_EolParserRules(input, state, this);
            gErlParserRules = new Edl_ErlParserRules(input, state, this);
            gEdlParserRules = new Edl_EdlParserRules(input, state, this);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EdlParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g"; }


    public static class edlModule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start edlModule
    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:93:1: edlModule : ( importStatement )* ( edlModuleContent )* EOF -> ^( EDLMODULE ( importStatement )* ( edlModuleContent )* ) ;
    public final EdlParser.edlModule_return edlModule() throws RecognitionException {
        EdlParser.edlModule_return retval = new EdlParser.edlModule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token EOF3=null;
        Edl_EolParserRules.importStatement_return importStatement1 = null;

        EdlParser.edlModuleContent_return edlModuleContent2 = null;


        org.eclipse.epsilon.common.parse.AST EOF3_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_edlModuleContent=new RewriteRuleSubtreeStream(adaptor,"rule edlModuleContent");
        RewriteRuleSubtreeStream stream_importStatement=new RewriteRuleSubtreeStream(adaptor,"rule importStatement");
        try {
            // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:94:2: ( ( importStatement )* ( edlModuleContent )* EOF -> ^( EDLMODULE ( importStatement )* ( edlModuleContent )* ) )
            // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:94:4: ( importStatement )* ( edlModuleContent )* EOF
            {
            // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:94:4: ( importStatement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==96) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:0:0: importStatement
            	    {
            	    pushFollow(FOLLOW_importStatement_in_edlModule76);
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

            // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:94:21: ( edlModuleContent )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==Annotation||(LA2_0>=91 && LA2_0<=92)||LA2_0==97||(LA2_0>=153 && LA2_0<=154)||LA2_0==157) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:94:22: edlModuleContent
            	    {
            	    pushFollow(FOLLOW_edlModuleContent_in_edlModule80);
            	    edlModuleContent2=edlModuleContent();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_edlModuleContent.add(edlModuleContent2.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_edlModule84); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF3);



            // AST REWRITE
            // elements: importStatement, edlModuleContent
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 95:2: -> ^( EDLMODULE ( importStatement )* ( edlModuleContent )* )
            {
                // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:95:5: ^( EDLMODULE ( importStatement )* ( edlModuleContent )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(EDLMODULE, "EDLMODULE"), root_1);

                // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:95:17: ( importStatement )*
                while ( stream_importStatement.hasNext() ) {
                    adaptor.addChild(root_1, stream_importStatement.nextTree());

                }
                stream_importStatement.reset();
                // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:95:34: ( edlModuleContent )*
                while ( stream_edlModuleContent.hasNext() ) {
                    adaptor.addChild(root_1, stream_edlModuleContent.nextTree());

                }
                stream_edlModuleContent.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end edlModule

    public static class edlModuleContent_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start edlModuleContent
    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:98:1: edlModuleContent : ( pre | annotationBlock | processRule | operationDeclaration | post );
    public final EdlParser.edlModuleContent_return edlModuleContent() throws RecognitionException {
        EdlParser.edlModuleContent_return retval = new EdlParser.edlModuleContent_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Edl_ErlParserRules.pre_return pre4 = null;

        Edl_EolParserRules.annotationBlock_return annotationBlock5 = null;

        Edl_EdlParserRules.processRule_return processRule6 = null;

        Edl_EolParserRules.operationDeclaration_return operationDeclaration7 = null;

        Edl_ErlParserRules.post_return post8 = null;



        try {
            // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:99:2: ( pre | annotationBlock | processRule | operationDeclaration | post )
            int alt3=5;
            switch ( input.LA(1) ) {
            case 153:
                {
                alt3=1;
                }
                break;
            case Annotation:
            case 97:
                {
                alt3=2;
                }
                break;
            case 157:
                {
                alt3=3;
                }
                break;
            case 91:
            case 92:
                {
                alt3=4;
                }
                break;
            case 154:
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
                    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:99:4: pre
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_pre_in_edlModuleContent108);
                    pre4=pre();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pre4.getTree());

                    }
                    break;
                case 2 :
                    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:99:10: annotationBlock
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_annotationBlock_in_edlModuleContent112);
                    annotationBlock5=annotationBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, annotationBlock5.getTree());

                    }
                    break;
                case 3 :
                    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:99:28: processRule
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_processRule_in_edlModuleContent116);
                    processRule6=processRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, processRule6.getTree());

                    }
                    break;
                case 4 :
                    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:99:42: operationDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_operationDeclaration_in_edlModuleContent120);
                    operationDeclaration7=operationDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operationDeclaration7.getTree());

                    }
                    break;
                case 5 :
                    // /Users/dkolovos/git/org.eclipse.epsilon/examples/org.eclipse.epsilon.edl.engine/src/org/eclipse/epsilon/edl/parse/Edl.g:99:65: post
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_post_in_edlModuleContent124);
                    post8=post();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, post8.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end edlModuleContent

    // Delegated rules
    public Edl_EolParserRules.featureCall_return featureCall() throws RecognitionException { return gEolParserRules.featureCall(); }
    public Edl_EolParserRules.transactionStatement_return transactionStatement() throws RecognitionException { return gEolParserRules.transactionStatement(); }
    public Edl_EolParserRules.breakStatement_return breakStatement() throws RecognitionException { return gEolParserRules.breakStatement(); }
    public Edl_EolParserRules.assignmentStatement_return assignmentStatement() throws RecognitionException { return gEolParserRules.assignmentStatement(); }
    public Edl_EolParserRules.typeName_return typeName() throws RecognitionException { return gEolParserRules.typeName(); }
    public Edl_EolParserRules.formalParameter_return formalParameter() throws RecognitionException { return gEolParserRules.formalParameter(); }
    public Edl_EolParserRules.defaultStatement_return defaultStatement() throws RecognitionException { return gEolParserRules.defaultStatement(); }
    public Edl_EolParserRules.additiveExpression_return additiveExpression() throws RecognitionException { return gEolParserRules.additiveExpression(); }
    public Edl_EolParserRules.statement_return statement() throws RecognitionException { return gEolParserRules.statement(); }
    public Edl_EolParserRules.relationalExpression_return relationalExpression() throws RecognitionException { return gEolParserRules.relationalExpression(); }
    public Edl_EolParserRules.block_return block() throws RecognitionException { return gEolParserRules.block(); }
    public Edl_EolParserRules.logicalExpressionInBrackets_return logicalExpressionInBrackets() throws RecognitionException { return gEolParserRules.logicalExpressionInBrackets(); }
    public Edl_EolParserRules.executableAnnotation_return executableAnnotation() throws RecognitionException { return gEolParserRules.executableAnnotation(); }
    public Edl_EolParserRules.shortcutOperatorExpression_return shortcutOperatorExpression() throws RecognitionException { return gEolParserRules.shortcutOperatorExpression(); }
    public Edl_EolParserRules.statementA_return statementA() throws RecognitionException { return gEolParserRules.statementA(); }
    public Edl_EolParserRules.logicalExpression_return logicalExpression() throws RecognitionException { return gEolParserRules.logicalExpression(); }
    public Edl_ErlParserRules.guard_return guard() throws RecognitionException { return gErlParserRules.guard(); }
    public Edl_EolParserRules.pathName_return pathName() throws RecognitionException { return gEolParserRules.pathName(); }
    public Edl_EolParserRules.postfixExpression_return postfixExpression() throws RecognitionException { return gEolParserRules.postfixExpression(); }
    public Edl_EolParserRules.keyvalExpression_return keyvalExpression() throws RecognitionException { return gEolParserRules.keyvalExpression(); }
    public Edl_EolParserRules.caseStatement_return caseStatement() throws RecognitionException { return gEolParserRules.caseStatement(); }
    public Edl_EolParserRules.modelDriver_return modelDriver() throws RecognitionException { return gEolParserRules.modelDriver(); }
    public Edl_EolParserRules.nativeType_return nativeType() throws RecognitionException { return gEolParserRules.nativeType(); }
    public Edl_EolParserRules.literalSequentialCollection_return literalSequentialCollection() throws RecognitionException { return gEolParserRules.literalSequentialCollection(); }
    public Edl_EolParserRules.annotationBlock_return annotationBlock() throws RecognitionException { return gEolParserRules.annotationBlock(); }
    public Edl_EolParserRules.keyvalExpressionList_return keyvalExpressionList() throws RecognitionException { return gEolParserRules.keyvalExpressionList(); }
    public Edl_EolParserRules.importStatement_return importStatement() throws RecognitionException { return gEolParserRules.importStatement(); }
    public Edl_EolParserRules.returnStatement_return returnStatement() throws RecognitionException { return gEolParserRules.returnStatement(); }
    public Edl_EolParserRules.whileStatement_return whileStatement() throws RecognitionException { return gEolParserRules.whileStatement(); }
    public Edl_EolParserRules.modelDeclarationParameter_return modelDeclarationParameter() throws RecognitionException { return gEolParserRules.modelDeclarationParameter(); }
    public Edl_EolParserRules.abortStatement_return abortStatement() throws RecognitionException { return gEolParserRules.abortStatement(); }
    public Edl_EolParserRules.expressionListOrRange_return expressionListOrRange() throws RecognitionException { return gEolParserRules.expressionListOrRange(); }
    public Edl_EolParserRules.continueStatement_return continueStatement() throws RecognitionException { return gEolParserRules.continueStatement(); }
    public Edl_ErlParserRules.extendz_return extendz() throws RecognitionException { return gErlParserRules.extendz(); }
    public Edl_EolParserRules.ifStatement_return ifStatement() throws RecognitionException { return gEolParserRules.ifStatement(); }
    public Edl_EolParserRules.variableDeclarationExpression_return variableDeclarationExpression() throws RecognitionException { return gEolParserRules.variableDeclarationExpression(); }
    public Edl_EolParserRules.expressionRange_return expressionRange() throws RecognitionException { return gEolParserRules.expressionRange(); }
    public Edl_EolParserRules.throwStatement_return throwStatement() throws RecognitionException { return gEolParserRules.throwStatement(); }
    public Edl_EolParserRules.switchStatement_return switchStatement() throws RecognitionException { return gEolParserRules.switchStatement(); }
    public Edl_EolParserRules.modelDeclaration_return modelDeclaration() throws RecognitionException { return gEolParserRules.modelDeclaration(); }
    public Edl_EolParserRules.expressionStatement_return expressionStatement() throws RecognitionException { return gEolParserRules.expressionStatement(); }
    public Edl_EdlParserRules.processRule_return processRule() throws RecognitionException { return gEdlParserRules.processRule(); }
    public Edl_EolParserRules.modelAlias_return modelAlias() throws RecognitionException { return gEolParserRules.modelAlias(); }
    public Edl_EolParserRules.statementB_return statementB() throws RecognitionException { return gEolParserRules.statementB(); }
    public Edl_EolParserRules.packagedType_return packagedType() throws RecognitionException { return gEolParserRules.packagedType(); }
    public Edl_EolParserRules.formalParameterList_return formalParameterList() throws RecognitionException { return gEolParserRules.formalParameterList(); }
    public Edl_EolParserRules.deleteStatement_return deleteStatement() throws RecognitionException { return gEolParserRules.deleteStatement(); }
    public Edl_EolParserRules.annotation_return annotation() throws RecognitionException { return gEolParserRules.annotation(); }
    public Edl_EolParserRules.statementBlock_return statementBlock() throws RecognitionException { return gEolParserRules.statementBlock(); }
    public Edl_EolParserRules.expressionList_return expressionList() throws RecognitionException { return gEolParserRules.expressionList(); }
    public Edl_EolParserRules.forStatement_return forStatement() throws RecognitionException { return gEolParserRules.forStatement(); }
    public Edl_EolParserRules.operationDeclaration_return operationDeclaration() throws RecognitionException { return gEolParserRules.operationDeclaration(); }
    public Edl_EolParserRules.simpleFeatureCall_return simpleFeatureCall() throws RecognitionException { return gEolParserRules.simpleFeatureCall(); }
    public Edl_EolParserRules.collectionType_return collectionType() throws RecognitionException { return gEolParserRules.collectionType(); }
    public Edl_ErlParserRules.post_return post() throws RecognitionException { return gErlParserRules.post(); }
    public Edl_EolParserRules.primitiveExpression_return primitiveExpression() throws RecognitionException { return gEolParserRules.primitiveExpression(); }
    public Edl_EolParserRules.itemSelectorExpression_return itemSelectorExpression() throws RecognitionException { return gEolParserRules.itemSelectorExpression(); }
    public Edl_EolParserRules.parameterList_return parameterList() throws RecognitionException { return gEolParserRules.parameterList(); }
    public Edl_EolParserRules.newExpression_return newExpression() throws RecognitionException { return gEolParserRules.newExpression(); }
    public Edl_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock() throws RecognitionException { return gEolParserRules.statementOrStatementBlock(); }
    public Edl_EolParserRules.operationDeclarationOrAnnotationBlock_return operationDeclarationOrAnnotationBlock() throws RecognitionException { return gEolParserRules.operationDeclarationOrAnnotationBlock(); }
    public Edl_EolParserRules.breakAllStatement_return breakAllStatement() throws RecognitionException { return gEolParserRules.breakAllStatement(); }
    public Edl_EolParserRules.literal_return literal() throws RecognitionException { return gEolParserRules.literal(); }
    public Edl_EolParserRules.declarativeFeatureCall_return declarativeFeatureCall() throws RecognitionException { return gEolParserRules.declarativeFeatureCall(); }
    public Edl_EolParserRules.modelDeclarationParameters_return modelDeclarationParameters() throws RecognitionException { return gEolParserRules.modelDeclarationParameters(); }
    public Edl_EolParserRules.elseStatement_return elseStatement() throws RecognitionException { return gEolParserRules.elseStatement(); }
    public Edl_EolParserRules.literalMapCollection_return literalMapCollection() throws RecognitionException { return gEolParserRules.literalMapCollection(); }
    public Edl_ErlParserRules.pre_return pre() throws RecognitionException { return gErlParserRules.pre(); }
    public Edl_EolParserRules.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gEolParserRules.multiplicativeExpression(); }
    public Edl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock() throws RecognitionException { return gEolParserRules.expressionOrStatementBlock(); }
    public Edl_EolParserRules.unaryExpression_return unaryExpression() throws RecognitionException { return gEolParserRules.unaryExpression(); }


 

    public static final BitSet FOLLOW_importStatement_in_edlModule76 = new BitSet(new long[]{0x0000000000800000L,0x0000000318000000L,0x0000000026000000L});
    public static final BitSet FOLLOW_edlModuleContent_in_edlModule80 = new BitSet(new long[]{0x0000000000800000L,0x0000000218000000L,0x0000000026000000L});
    public static final BitSet FOLLOW_EOF_in_edlModule84 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_in_edlModuleContent108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationBlock_in_edlModuleContent112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_processRule_in_edlModuleContent116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationDeclaration_in_edlModuleContent120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_post_in_edlModuleContent124 = new BitSet(new long[]{0x0000000000000002L});

}