package confidentialmessenger;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 *
 * @author Shershnev
 */
public class RSAcryptosystem {    
    public RSAcryptosystem() {
        e = BigInteger.ONE;
        n = BigInteger.ONE;
        myD = BigInteger.ONE;
        myN = BigInteger.ONE;
    }
    
    public void setOpenKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }
    
    public void setCloseKey(BigInteger d, BigInteger n) {
        this.myD = d;
        this.myN = n;
    }
    
    public BigInteger getOpenE() {
        return e;
    }
    
    public BigInteger getOpenN() {
        return n;
    }
    
    public BigInteger encrypt(BigInteger m) {       
        return m.modPow(e,n);
    }            
    
    public BigInteger decrypt(BigInteger code) {        
        return code.modPow(myD,myN);
    }
    
    public BigInteger getSignature(BigInteger m) {
        return m.modPow(myD,myN);
    }
    
    public BigInteger decryptSignature(BigInteger code) {
        return code.modPow(e,n);
    }
          
    public BigInteger stringToBigInteger(String str) {
        SecureRandom rnd = new SecureRandom();
        BigInteger ret = BigInteger.ZERO;
        int len = str.length();
        
        for (int i = 0; i < len; i++)
            ret = ret.multiply(BigInteger.valueOf(100000)).add(BigInteger.valueOf(str.charAt(i)));
        
        //добавление к сообщению случайных бит
        ret = ret.multiply(BigInteger.valueOf(100000));               
        while (len < 120) {
            ret = ret.multiply(BigInteger.valueOf(100000)).add(BigInteger.valueOf(rnd.nextInt(99999)));
            len++;            
        }        
        
        return ret;
    }
    
    public String bigIntegerToString(BigInteger ret) {
        String str = "";
        int check = 1;
        
        //убираем случайные биты
        while (check != 0) {
            check = ret.mod(BigInteger.valueOf(100000)).intValue();
            ret = ret.divide(BigInteger.valueOf(100000));
        }
        
        check = ret.mod(BigInteger.valueOf(100000)).intValue();        
        ret = ret.divide(BigInteger.valueOf(100000));
        while (check != 0) {
            str += (char)check;
            check = ret.mod(BigInteger.valueOf(100000)).intValue();                        
            ret = ret.divide(BigInteger.valueOf(100000));
        }                    
        
        return new StringBuilder(str).reverse().toString();
    }
    
    private BigInteger e, n, myD, myN;  
}
