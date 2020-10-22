package edu.njit.cs114;

import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 10/13/20
 */
public class ExpressionEvaluator {

    // Complete this function which can be called by postfixEval()
    private static double applyOp(OperatorToken opType, double op1, double op2) {
        if(opType.equals(OperatorToken.ADD)){
            return op1+op2;
        }
        else if(opType.equals(OperatorToken.SUBTRACT)){
            return op1-op2;
        }
        else if(opType.equals(OperatorToken.MULTIPLY)){
            return op1*op2;
        }
        else if(opType.equals(OperatorToken.DIVIDE)){
            return op1/op2;
        }
        else if(opType.equals(OperatorToken.EXP)){
            return Math.pow(op1, op2);
        }
        return 0.0;
    }

    /**
     * Parse an expression into a list of operator and operand tokens
     * @param str
     * @return
     */
    public static List<ExpressionToken> parseExpr(String str) {
        List<ExpressionToken> expr = new ArrayList<>();
        String [] strTokList = str.split("\\s+");
        for (String strTok : strTokList) {
            String str1 = strTok.trim();
            if (str1.isEmpty()) {
                continue;
            }
            OperatorToken operToken = OperatorToken.opType(str1);
            expr.add(operToken == null ? new OperandToken(str1) : operToken);
        }
        return expr;
    }

    /**
     * Convert Infix expression given as a list of operator and operand tokens to
     * a postfix expression as a list of operator and operand tokens
     * @param infixExpr
     * @return
     * @throws Exception when the expression is not valid
     *    such as insufficient number of operators or operands e.g. 4 * 2 5, 4 * 2 +
     *    or not having balanced parentheses e.g. (4 * ( 5 + 3 )
     */
    public static List<ExpressionToken> convertToPostFix(List<ExpressionToken> infixExpr) throws Exception {
        /**
         * Complete the code here only for homework.
         * After completing the code here, remove the following return statement
         */

        List<ExpressionToken> result = new LinkedList<>();
        Stack<OperatorToken> operatorStack = new Stack<>();

        for(ExpressionToken tok: infixExpr){
            if(tok instanceof OperandToken){
                result.add(tok);
            }else{
                OperatorToken operTok = (OperatorToken)tok;
                if(operTok.equals(OperatorToken.OPENPAR)){
                    operatorStack.push(operTok);
                }else if(operTok.equals(OperatorToken.CLOSEDPAR)){
                    try {
                        while (operatorStack.peek() != OperatorToken.OPENPAR) {
                            result.add(operatorStack.pop());
                        }
                        operatorStack.pop();
                    }catch(Exception e){
                        throw e;
                    }
                }else{
                    while(!operatorStack.empty()){
                        if(!operTok.precedes(operatorStack.peek())){
                            result.add(operatorStack.pop());
                        }else{
                            break;
                        }
                    }
                    operatorStack.push(operTok);
                }

            }
        }
        while(!operatorStack.empty()){
            result.add(operatorStack.pop());
        }

        return result;
    }

    /**
     * Evaluate post fix expression given as a list of operator and operand tokens
     * and return the result
     * @param postfixExpr
     * @return
     * @throws Exception when the expression is not valid
     *      such as insufficient number of operators or operands e.g. 4 5 2 *, 4 *
     */
    public static double postFixEval(List<ExpressionToken> postfixExpr) throws Exception {
        /**
         * Complete this code for lab
         * After completing the code here, remove the following return statement
         */

        Stack<Double> stack = new Stack<>();
        Double temp1 = 0.0;
        Double temp2 = 0.0;

        for(ExpressionToken x: postfixExpr){
            if(x instanceof OperandToken){
                stack.push(((OperandToken) x).getValue());
                //continues
            }
            else if(x instanceof OperatorToken){
                if(x==OperatorToken.ADD || x==OperatorToken.DIVIDE || x==OperatorToken.EXP || x==OperatorToken.MULTIPLY || x==OperatorToken.SUBTRACT){
                    try {
                        temp2 = stack.pop();
                        temp1 = stack.pop();
                        stack.push(applyOp(((OperatorToken) x), temp1, temp2));
                    }catch(Exception e){
                        throw new Exception("Insufficient number of operands in post fix expression " + postfixExpr);
                    }
                }
            }
        }

        double finalNum = stack.pop();
        if(!stack.empty()){
            throw new Exception("Insufficient number of operators in post fix expression " + postfixExpr);

        }
        return finalNum;
    }

    /**
     * Evaluate an infix expression string using postfix
     * @param str
     * @return
     * @throws Exception when the expression is not valid (e.g 2 + 3 5)
     */
    public static double eval(String str) throws Exception {
        return postFixEval(convertToPostFix(parseExpr(str)));
    }

    /**
     * Evaluate an infix expression string directly
     * @param str
     * @return
     * @throws Exception
     */
    public static double evalDirect(String str) throws Exception {
        List<ExpressionToken> tokens = parseExpr(str);
        /**
         * For homework extra credit only!!
         */
        return 0;
    }

    public static void main(String [] args) throws Exception {
        /** Uncomment commented lines below after you have finished the homework implementations **/
          System.out.println(String.format("expr. %s evaluated as %.4f", "( 8 - 6 ) * ( 6 / 2 ) + 3",
            eval("( 8 - 6 ) * ( 6 / 2 ) + 3")));
        System.out.println(String.format("postfix notation for %s : %s", "-6.5",
                convertToPostFix(parseExpr("-6.5"))));
        double result = postFixEval(parseExpr("-6.5"));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f", "-6.5",
                result));
        assert Math.abs(result-(-6.5)) <= 0.00001;
        System.out.println(String.format("%s evaluated as %.4f", "-6.5",
              eval("-6.5")));
        System.out.println(String.format("postfix notation for %s : %s", " 5 * -2",
                convertToPostFix(parseExpr(" 5 * -2"))));
        result = postFixEval(parseExpr(" 5 -2 *"));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f", " 5 -2 *",
                result));
        assert Math.abs(result-(-10.0)) <= 0.00001;
        System.out.println(String.format("%s evaluated as %.4f", " 5 * -2",
                eval(" 5 * -2")));
        System.out.println(String.format("postfix notation for %s : %s", "( 4 + -2 ) * 7",
                convertToPostFix(parseExpr("( 4 + -2 ) * 7"))));
        System.out.println(String.format("%s evaluated as %.4f", "( 4 + -2 ) * 7",
                eval("( 4 + -2 ) * 7")));
        System.out.println(String.format("postfix notation for %s : %s", " 4 * ( 3 - 2 ) ) ** -2 ",
                convertToPostFix(parseExpr(" 4 * ( 3 - 2 ) ) ** -2 "))));
        System.out.println(String.format("%s evaluated as %.4f", " 4 * ( 3 - 2 ) ) ** -2",
                   eval(" 4 * ( 3 - 2 ) ) ** -2")));
        System.out.println(String.format("postfix notation for %s : %s",
                " ( ( 1.5 + 2.1 ) ** 2  - 7 ) * -1.4",
                convertToPostFix(parseExpr(" 4 * ( 3 - 2 ) ) ** -2 "))));
        result = postFixEval(parseExpr("1.5 2.1 + 2 ** 7 - -1.4 *"));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f",
                "1.5 2.1 + 2 ** 7 - -1.4 *", result));
        assert Math.abs(result-(-8.344)) <= 0.00001;
       System.out.println(String.format("%s evaluated as %.4f",
                " ( ( 1.5 + 2.1 ) ** 2  - 7 ) * -1.4",
                eval(" ( ( 1.5 + 2.1 ) ** 2  - 7 ) * -1.4")));
        result = postFixEval(parseExpr("4 -2 7 * +"));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f", "4 -2 7 * +",
                result));
        assert Math.abs(result-(-10.0)) <= 0.00001;
        result = postFixEval(parseExpr("3.5 2 3 + /"));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f", "3.5 2 3 + /",
                result));
        assert Math.abs(result-(0.7)) <= 0.00001;
        System.out.println(String.format("%s evaluated as %.4f", "3.5 / ( 2 + 3 )",
                eval("3.5 / ( 2 + 3 )")));
        System.out.println(String.format("postfix notation for %s : %s", "2 ** 3 ** 2",
                convertToPostFix(parseExpr("2 ** 3 ** 2"))));
        System.out.println(String.format("%s evaluated as %.4f", "2 ** 3 ** 2",
                eval("2 ** 3 ** 2")));
        try {
            System.out.println(String.format("postfix expr. %s evaluated as %.4f", "2 2.5 + 3 4 *",
                    postFixEval(parseExpr("2 2.5 + 3 4 *"))));
            assert false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert true;
        }
        try {
            System.out.println(String.format("postfix expr. %s evaluated as %.4f", "2 5 * + ",
                    postFixEval(parseExpr("2 5 * + "))));
            assert false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assert true;
        }
    }

}
