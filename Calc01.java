
import java.util.*;

public class Calc01{

   public static  Queue<String> parce(String exp){
      Queue<String> parced = new LinkedList<>();
      Queue<String> noGood = new LinkedList<>();
      String signs = "+-*/^(){}";
      char[] comp = exp.toCharArray();
      for(int i = 0; i<comp.length; i++){
         if(comp[i] == 's'){
            if(i+1 >= comp.length) 
               return noGood;
            if(comp[i+1]=='i'){
               if(i+2 >= comp.length) 
                  return noGood;
               if(comp[i+2]=='n'){
                  parced.add("sin");
                  i = i+2;
               }else{
                  return noGood;
               }
            }else{
               return noGood;
            }
         
         }else if(comp[i] == 'c'){
            if(i+1 >= comp.length) 
               return noGood;
            if(comp[i+1]=='o'){
               if(i+2 >= comp.length) 
                  return noGood;
               if(comp[i+2]=='s'){
                  parced.add("cos");
                  i = i+2;
               }else if(comp[i+1]=='o'){
                  if(i+2 >= comp.length) 
                     return noGood;
                  if(comp[i+2]=='t'){
                     parced.add("cot");
                     i = i+2;
                  }
               
               
               }else{
                  return noGood;
               }
            }else{
               return noGood;
            }
         
         }else if(comp[i] == 't'){
            if(i+1 >= comp.length) 
               return noGood;
            if(comp[i+1]=='a'){
               if(i+2 >= comp.length) 
                  return noGood;
               if(comp[i+2]=='n'){
                  parced.add("tan");
                  i = i+2;
               }else{
                  return noGood;
               }
            }else{
               return noGood;
            }
         
         }else if(comp[i] == 'l'){
            if(i+1 >= comp.length) 
               return noGood;
            if(comp[i+1]=='n'){
               parced.add("ln");
               i++;
            }else if(comp[i+1]=='g'){
               //if(i+2 >= comp.length) 
                  //return noGood;
               parced.add("lg");
               i++;               
            }else{
               return noGood;
            }
         
         
         }else if(Character.isDigit(comp[i])){
            String temp = "";
            boolean dec = false; 
            while(i<comp.length){
               
               if(Character.isDigit(comp[i])){
                  //System.out.println("if"+i);
                  temp += comp[i];
                  i++;               
               }else if(Character.isDigit(comp[i-1]) && comp[i] == '.'){
                  //System.out.println("if"+i);
                  temp += comp[i];
                  i++;
                  if(dec == false){
                     dec = true;  
                  }else{
                     return noGood;
                  }             
               }else{
                  break;
               }
            }
            parced.add(temp);
            i--;
         }else if (signs.contains(String.valueOf(comp[i]))){
            //System.out.println(comp[i]);
            if (comp[i] == '+' ){
               if(i+1<comp.length && comp[i+1] == '-'){
                  parced.add("-");
                  i++;
               }else{
                  parced.add(String.valueOf(comp[i]));
               }
            }else if(comp[i] == '-' ){
               if(i+1<comp.length && comp[i+1] == '-'){
                  parced.add("+");
                  i++;
               }else if(i+1<comp.length && Character.isDigit(comp[i+1]) ){
                  
                  if(i == 0 || (signs.contains(String.valueOf(comp[i-1])) && !(comp[i-1] == ')' ))){
                     
                     String temp = "-";
                     i++;
                     while(i<comp.length){
                     
                        if(Character.isDigit(comp[i])){
                        //System.out.println("if"+i);
                           temp += comp[i];
                           i++;               
                        }else if(Character.isDigit(comp[i-1]) && comp[i] == '.'){
                        //System.out.println("if"+i);
                           temp += comp[i];
                           i++;               
                        }else{
                           break;
                        }
                     }
                     parced.add(temp);
                     i--;
                  }else{
                     parced.add("-");
                  
                  }
                  
                  
               }else {
                  parced.add("-");
               }
            }else{
               parced.add(String.valueOf(comp[i]));
            }
         }else{
            return noGood;
         }
      }
      return parced;
   }
   public static boolean compare(String o, String p){
      if(o.equals("(")||o.equals("{")){
         return true;
      }
      
      String[] sines = {"+-","*/","^"};
      int oo = -1;
      int pp = -1;
      
      if(sines[0].contains(o)){
         oo = 0;
      }else if(sines[1].contains(o)){
         oo = 1;
      }else if(sines[2].contains(o)){
         oo = 2;
      }
      if(sines[0].contains(p)){
         pp = 0;
      }else if(sines[1].contains(p)){
         pp = 1;
      }else if(sines[2].contains(p)){
         pp = 2;
      }
      if(pp>oo){
         return true;
      }else{
         return false;
      }
   
   }
   public static boolean isSDigit(String s){
      char[] c = s.toCharArray();
      if(Character.isDigit(c[0])){
         return true;
      }else if (c[0] == '-' ){
         if(c.length>1 && Character.isDigit(c[1])){
         
            return true;
         }else{
            return false;
         }
      }else{
         return false;
      }
   }
   public static Stack<String> RPN(Queue<String> parced){
      Stack<String> postfix = new Stack<>();
      Stack<String> operator = new Stack<>();
      Stack<String> err = new Stack<>();
      err.push("error");
      String other = "sincostancotlnlg";
      String signs = "+-*/^";
      int curlyOn = 0;
      int bracketOn = 0;
      
      if(!parced.peek().equals("-") && (signs.contains(parced.peek()) || parced.peek().equals(")") || parced.peek().equals("}")))
         return err;//if the experation starts inappropraitly 
            
      while(!parced.isEmpty()){
         String temp = parced.poll();
         
         //when the queue is containing +-*/^
         
         if(signs.contains(temp)){
         
            if(!parced.isEmpty() && other.contains(parced.peek()) || isSDigit(parced.peek()) || parced.peek().equals("(") || !signs.contains(parced.peek())){
               if(operator.isEmpty()){
                  operator.push(temp);
               }else{
                  if(compare(operator.peek(),temp)){//if temp have higher presedence
                     operator.push(temp);
                  }else{
                     postfix.push(operator.pop());
                     operator.push(temp);
                  }
               
               }
            }else{
               return err;
            }
         }   
          //when the queue is containing digits
         if(isSDigit(temp)){
            if(parced.isEmpty() || parced.peek().equals(")") || parced.peek().equals("}") || signs.contains(parced.peek())){
               postfix.push(temp);
            }else{
               return err;
            }
         }
         //special case for open prenthesis
         if(temp.equals("(")){
            if(!parced.isEmpty() && (other.contains(parced.peek()) || isSDigit(parced.peek())  || parced.peek().equals("-"))){
               operator.push(temp);
               bracketOn++;
            }else{ //making sure the calc dont accept sin() or (*) or ()
               return err;
            }
            
            
                    
         }
         
         if(temp.equals(")")){
            if(parced.isEmpty() || signs.contains(parced.peek()) || parced.peek().equals(")") || parced.peek().equals("}")){
               if(bracketOn==0){
                  return err;
               }
               while(!operator.isEmpty()){
                  String temp1 = operator.pop();
                  if(!temp1.equals("(")){
                     postfix.push(temp1);
                  }else{
                     if(!operator.isEmpty() && other.contains(operator.peek()))
                        postfix.push(operator.pop());
                     break;
                  }
               }
               bracketOn--;
            }else{
               return err;
            }
         
         }
         //special case for open curly brackets
         /*if(temp.equals("{")){ 
            operator.push(temp);
            curlyOn++;
            
                    
         }*/
         
         if(temp.equals("{")){
            if(!parced.isEmpty() && (other.contains(parced.peek()) || isSDigit(parced.peek())  || parced.peek().equals("(") || parced.peek().equals("-"))){
               operator.push(temp);
               curlyOn++;
            }else{ //making sure the calc dont accept sin() or (*) or ()
               return err;
            }
            
            
                    
         }
         
         /*if(temp.equals("}")){
            if(curlyOn==0){
               return err;
            }
            while(!operator.isEmpty()){
               String temp1 = operator.pop();
               if(!temp1.equals("{")){
                  postfix.push(temp1);
               }else{
                  break;
               }
            }
            curlyOn--;
         
         }*/
         if(temp.equals("}")){
            if(parced.isEmpty() || signs.contains(parced.peek()) || parced.peek().equals(")") || parced.peek().equals("}")){
               if(curlyOn==0){
                  return err;
               }
               while(!operator.isEmpty()){
                  String temp1 = operator.pop();
                  if(!temp1.equals("{")){
                     postfix.push(temp1);
                  }else{
                     if(!operator.isEmpty() && other.contains(operator.peek()))
                        postfix.push(operator.pop());
                     break;
                  }
               }
               curlyOn--;
            }else{
               return err;
            }
         
         }
         //dealing with sin cos tan cot ln and lg
         if(temp.equals("sin")){
            operator.push(temp);
            if(!parced.peek().equals("(") && !parced.peek().equals("{") ) 
               return err;
         }else if(temp.equals("cos")){
            operator.push(temp);
            if(!parced.peek().equals("(") && !parced.peek().equals("{")) 
               return err;
         }else if(temp.equals("tan")){
            operator.push(temp);
            if(!parced.peek().equals("(") && !parced.peek().equals("{")) 
               return err;
         }else if(temp.equals("cot")){
            operator.push(temp);
            if(!parced.peek().equals("(") && !parced.peek().equals("{")) 
               return err;
         }else if(temp.equals("ln")){
            operator.push(temp);
            if(!parced.peek().equals("(") && !parced.peek().equals("{")) 
               return err;
         }else if(temp.equals("lg")){
            operator.push(temp);
            if(!parced.peek().equals("(") && !parced.peek().equals("{")) 
               return err;
         }
      
      
      
      
      }//end of main while
      
      
      //finalizing steps
      if(bracketOn!=0 || curlyOn != 0){
         return err;
      }      
      while(!operator.isEmpty()){
         String temp = operator.pop();
         postfix.push(temp);
      }
      
      
      return postfix;
   }
   
   public static Stack<String> reverseStack(Stack<String> originalStack) {//chat gbt
      Stack<String> reversedStack = new Stack<>();
   
      while (!originalStack.isEmpty()) {
         String element = originalStack.pop();
         reversedStack.push(element);
      }
   
      return reversedStack;
   }
    
   public static double evaluate (Stack<String> postfix){
      
      Stack<String> rev = reverseStack(postfix);
      Stack<Double> elements = new Stack<>();
      String signs = "+-*/^";
      String other = "sincostancotlnlg";
      while(!rev.isEmpty()){
         String temp = rev.pop();
         if(isSDigit(temp)){
            elements.push(Double.parseDouble(temp));
         }else if (signs.contains(temp)){
            double a = elements.pop();
            double b = 0;
            if(!elements.isEmpty()){
                b = elements.pop();
            }
            double r = 0;
            if(temp.equals("+")){
               r = a + b;
               elements.push(r);
            }else if(temp.equals("-")){
               r = b-a;
               elements.push(r);
            }else if(temp.equals("/")){
               r = b/a;
               elements.push(r);
            }else if(temp.equals("*")){
               r = a * b;
               elements.push(r);
            }else if(temp.equals("^")){
               r = Math.pow(b,a);
               elements.push(r);
            }
         }else if (other.contains(temp)){
            double a = elements.pop();
            double r = 0;
            if(temp.equals("sin")){
               r=Math.sin(a);
               elements.push(r);
            }else if(temp.equals("cos")){
               r=Math.cos(a);
               elements.push(r);
            
            }else if(temp.equals("tan")){
               r=Math.tan(a);
               elements.push(r);
            
            }else if(temp.equals("cot")){
               r=1/Math.tan(a);
               elements.push(r);
            
            }else if(temp.equals("ln")){
               r=Math.log(a);
               elements.push(r);
            
            }else if(temp.equals("lg")){
               r=Math.log10(a);
               elements.push(r);
            
            }
         }
         
      }
      
      
      return elements.pop();
    
   }
   
   public static void main( String[] args){
      String input = "";
      while(!input.equals("stop")){
         System.out.println("Enter an arthimatic experession");
         Scanner keyboard = new Scanner(System.in);
         input = keyboard.nextLine();
         input = input.replace(" ","");
         if(input.equals("stop")){
            System.out.println("Thank you for using my calculator");
            continue;
         }
         Queue<String> parced = parce(input);
         System.out.println(parced);
         if(parced.isEmpty()){
            System.out.println("Invalid input");
            continue;
         }
         Stack<String> postfix = new Stack<>();
         postfix = RPN(parced);
         System.out.println(postfix);
         if(postfix.peek().equals("error")){
            System.out.println("Invalid input");
            continue;
         }
         System.out.println("Result: " + evaluate(postfix));
         
      
      }
   
   }
}