import java.util.Stack;
/**
* Takes an infix expression and evaluates it
 */
public class InfixEvaluator {

	/** A list of operators. */
	private static final String OPERATORS = "()+-*/%^";
	private static Stack<Integer> operandStack;
	private static Stack<Character> operatorStack;

	public static int evaluate( String expression ) {
		
		infixConvert(expression);
		
		while(!operatorStack.empty()){
			compute();
		}
		if(operatorStack.empty()&&operandStack.size()>1){
			throw new SyntaxErrorException("INVALID EXPRESSION--NOT ENOUGH OPERATORS");
		}
		return operandStack.pop();
	}

	/**
	 * Checks to find out if the token being passed in an operator or not
	 * @param The character to check
	 * @return If character is an operator
	 */
	private static boolean isOperator(char ch) {
		return OPERATORS.indexOf(ch) != -1;
	}

	/**
	 * Checks to see the precedence of an operator
	 * @param op the operator to evaluate precedence on 
	 * @return the operators precedence number (higher means greater precedence)
	 * @exception Throws exception if there is an invalid operator passed
	 */
	private static int precedence(char op){
		//Ranks precedences scale of 1-3
		if(op == '^')
			return 3;
		else if(op == '*'||op == '/' || op == '%')
			return 2;
		else if(op == '+'||op == '-')
			return 1;
		else if(op == '(')
			return -1;
		else if(op == ')')
			return 0;
		else throw new SyntaxErrorException("INVALID OPERATOR");
	}

	/**
	 * Performs the operation on the operand stack including each mathematical operation
	 * @exception throws exception if operandstack is empty or becomes empty
	 */
	private static void compute(){
		char s = operatorStack.pop();
		//Checks for error if not enough operands
		if(operandStack.isEmpty()){
			throw new SyntaxErrorException("INVALID EXPRESSION--EMPTY OPERANDSTACK");
		}
		int right = operandStack.pop();
		//Checks for error if not enough operands after one pop
		if(operandStack.isEmpty()){
			throw new SyntaxErrorException("INVALID EXPRESSION--EMPTY OPERANDSTACK");
		}
		int left = operandStack.pop();
		if( s == '%'){
			operandStack.push(left%right);
		}
		if( s == '^'){
			operandStack.push((int) Math.pow(left, right));
		}
		if( s == '*'){
			operandStack.push(left*right);
		}
		if( s == '/'){
			if(right == 0){
				throw new SyntaxErrorException("INVALID EXPRESSION--DIVIDING BY 0");
			}
			else
				operandStack.push(left/right);
		}
		if( s == '+'){
			operandStack.push(left+right);
		}
		if( s == '-'){
			operandStack.push(left-right);
		}
	}

	/**
	 * Converts the expression into operandStack and operatorStack 
	 * @param the passed expression
	 * @exception Throws SystemErrorException if incorrect organization
	 */
	private static void infixConvert(String infix){
		String[] tokens = infix.split("\\s+");
		operandStack = new Stack<Integer>();
		operatorStack = new Stack<Character>();

		for(String nextToken : tokens ){
			char firstChar = nextToken.charAt(0);
			if(firstChar-'0'>=0&&firstChar-'0'<=9){
				try{
					int pushOperand = Integer.parseInt(nextToken);
					operandStack.push(pushOperand);
				}
				//Catch block to make sure it is a number not with any other operators
				catch (NumberFormatException e){ 
					throw new SyntaxErrorException("INVALID EXPRESSION--NO SPACE BETWEEN EXPRESSION");
				}
			}
			else if(isOperator(firstChar)){
				processOp(firstChar);
			}
			else{ //If expression has an invalid character
				throw new SyntaxErrorException("INVALID EXPRESSION--ILLEGAL CHARACTER");
			}
		}
	}
	
	/**
	 * Processes the operator passed to see if it will trigger computes or be added to 
	 * stack
	 * @param the operator to be processed
	 */
	private static void processOp(char op){
		if(operatorStack.empty()){
			operatorStack.push(op);
		}
		else{
			char topOp = operatorStack.peek();
			//Check to see if '('
			if(op == '(')
				operatorStack.push(op);

			else if(precedence(op) > precedence(topOp)){
				operatorStack.push(op);
			}
			//Loop to find where the operator is lower precedence than the stack
			else{	
				while(!operatorStack.empty()
						&&precedence(op) <= precedence(topOp)){
					compute();
					if(!operatorStack.empty()){
						topOp = operatorStack.peek();
					}
				}
				if(topOp == '(')
					operatorStack.pop();
				if(op!= ')')
					operatorStack.push(op);
			}
		}
	}
}
