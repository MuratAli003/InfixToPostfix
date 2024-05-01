class Stack{
    // İnfix ifadenin operatörlerini tutacağımız stack i burada oluşturacağız
    
    char operator[];
    int kapasite;
    int indis;
    
    Stack(int kapasite){
        
        this.kapasite = kapasite;
        this.operator = new char[kapasite];
        indis = -1;
        
    }
    
    boolean bosMu(){
        
        return indis == -1;// diziler 0. indisten başladığı için indisi -1 belirlemek daha mantıklı;
        
    }
    
    boolean doluMu(){
        
        return indis == kapasite-1; //diziye ekleme yaparken ilk önce indisi arttırdığımız için kapasite -1 de durdurmamız lazım. 
        
    }
    void push(char x){
        
        if(!doluMu()){
            
            indis++;// indis -1 den başladığı için ilk önce indisi arttırmamız lazım.
            operator[indis] = x;
            
        }
    }
    
    char pop(){
        
        if(!bosMu()){
            
            char silinen = operator[indis];// geri döndürmek için yedekliyoruz
            operator[indis] = ' '; 
            indis--;
            
            return silinen;// yedeklediğimiz değeri geri döndürdük
            
        }
        else{
            
            return ' ';
            
        }
    }
}

class Postfix{
    
    String islem;
    Stack stack;
    
    Postfix(String islem){
        
        
        this.islem = islem;
        stack = new Stack(islem.length());// stack i burada tanımlamamızın amacı islem ifadesi bu metodun içinde tanımlanmasından kaynaklı.
                                                 // aksi takdirde islem.length() ifadesi null olucaktır.    
    }
    
    boolean islemOnceligi(){
        
        // bu metod ile stack içerisinde işlem önceliği küçük olan operatörün önceliği büyük olanın üstüne gelip gelmediği kontrol edilir
        
        if(stack.indis >0 && (stack.operator[stack.indis] == '+' || stack.operator[stack.indis] =='-')){
            
            if(stack.operator[stack.indis-1] == '*' || stack.operator[stack.indis-1] == '/'){
                
                return false;
                
            }
        }
        
        return true;
        
    }
    
    public String infixToPostfix(){
        
        String yeni = "";// operatörler dışında kalan ifadeleri ilk başta buraya ekleyip ardından operatörleride buraya ekleyeceğiz
        
        int i = 0;
     
        while(i < islem.length()) {
            
            if(islem.charAt(i) == '{' || islem.charAt(i) == '(' || islem.charAt(i) == '['){
                
                stack.push(islem.charAt(i));// açılan parantezin işleme bir etkisi olmadığı için stacke ekliyoruz
                i++;
               
            }    
            else if(islem.charAt(i) == '}' || islem.charAt(i) == ']' || islem.charAt(i) == ')'){
               
                // parantez kapatıldığı zaman stack boşaltılmaya başlanır.
                while(!stack.bosMu()){
                    
                    if(stack.operator[stack.indis] == '{' || stack.operator[stack.indis] == '[' || stack.operator[stack.indis] == '('){
                                
                        stack.pop();// bu bloğa girmişse açık parantezi silip işlemi bırakmamız gerek, yoksa stack her boşaltıldığında 
                                            // bu parantezden sonraki elemanlara geçemez.     
                        break;
                                
                    }
                    else{
                        
                        yeni += stack.pop();// bu blokta her eleman stackten silinirken aynı zamanda postfix ifademize ekleme yapıyoruz
                    
                    }
                }
                
                i++;
                
            }
            
            else if(islem.charAt(i) == '+' || islem.charAt(i) == '-'){
                
                stack.push(islem.charAt(i));// işlem önceliği metodunda kontrol edebilmemiz için öncelikle bu ifadeyi eklememiz lazım
                                                   // eğer aşağıdaki if bloğuna girmezse ifade direkt eklenmiş olacak
                
                if(!islemOnceligi()){
                    
                    stack.pop();// islem önceliğini sağlamadığı için yukarıda eklediğimiz ifadeyi dahil etmeden stacki boşaltmamız lazım
                    
                    while(!stack.bosMu()){
                        
                        if(stack.operator[stack.indis] == '{' || stack.operator[stack.indis] == '[' || stack.operator[stack.indis] == '('){
                            
                            //bu if bloğundaki işlemin amaı eğer parantez içi işlem varsa yalnızca o parantez içindeki işlem bitene kadar işleme devam etmektir 
                               
                            stack.pop();
                            break;
                                
                    }
                        else{
                        
                            yeni += stack.pop();
                    
                        }
                    }
                    stack.push(islem.charAt(i)); // stack boşaltıldıktan sonra yeni stackimize ifademizi ekleyebiliriz
                }
                
                i++;
            }
            else if(islem.charAt(i) == '*' || islem.charAt(i) == '/'){
                
                stack.push(islem.charAt(i));// '*' ve '/' işlem önceliği en üst sırada olduğu için stack e koşulsuz eklenir
                                                   // !!parantez kontrolü zaten diğer if bloklarında yapıldı!!
                i++;

            }  
            else{
                
                // bu blokta operatörler dışındaki karakterler yazılır
                
                yeni += islem.charAt(i);
                i++;
            }
              
        }
        
        while(!stack.bosMu()){
            
            // bu döngüde eğer stackte çıkarılmayan ifade varsa o ifadede yeni ifademize eklenir
            
            yeni += stack.pop();
            
        }
        
        return yeni;
        
        }
}

public class InfixToPostfix {
    
    public static void main(String[] args) {
        
        String islem = "(a*(b+c)-d+(c*d))";

        Postfix donustur = new Postfix(islem);

        String sonuc = donustur.infixToPostfix();
        
        System.out.println(sonuc2);
     
    }
}

