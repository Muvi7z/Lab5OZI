package com.company;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        for(int i = 2; i < num; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }
    public static boolean IsCoprime(int num1, int num2)
    {
        if (num1 == num2)
        {
            return num1 == 1;
        }
        else {
            if(num1>num2){
                return IsCoprime(num1 - num2, num2);
            }
            else return IsCoprime(num2-num1,num1);
        }
    }
    public static void main(String[] args) {
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        String charRU = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";;
        System.out.print("Введите сообщение: ");
        String text = in.nextLine(); //23 29
        StringBuilder digest= new StringBuilder();
        for(int i=0; i<text.length(); i++){
            if(i%2!=0) digest.append((text.charAt(i)+"").toUpperCase(Locale.ROOT));
        }
        int p=19, q=23;
        int n = p*q;
        int form = (p-1)*(q-1); //ЗАКРЫТЫЙ КЛЮЧ

        int h0 =random.nextInt(200)+1;  // (h(i-1) + m(i)**2)%2
        int hash = h0;
        for(int i=0; i<digest.length(); i++){
            hash = ((int) Math.pow((hash + charRU.indexOf(digest.charAt(i))),2))%n;
        }
        System.out.println("Хеш образ сообщения \""+digest+"\" равен: "+hash);
        System.out.println("Генерация электронного ключа...");
        int d =random.nextInt(100);
        while (!IsCoprime(form,d)){
            d =random.nextInt(200);
        }
        int e = (form+1)/d;
        int temp=form;
        while ((e*d)%(temp)!=1){
            temp+=form;
            e = (temp+1)/d;
        }
        System.out.println("Закрытый ключ: "+d + ", "+n);
        System.out.println("Открытый ключ: "+e + ", "+n);
        BigInteger s = new BigInteger(String.valueOf(hash));
        s = s.pow(d);
        s = s.remainder(BigInteger.valueOf(n));
        System.out.println("Электронная подпись: "+s);
        System.out.println("Проверка ЭП..");

        System.out.print("Введите сообщение: ");
        text = in.nextLine(); //23 29
        digest= new StringBuilder();
        for(int i=0; i<text.length(); i++){
            if(i%2!=0) digest.append((text.charAt(i)+"").toUpperCase(Locale.ROOT));
        }
        int hashNew = h0;
        for(int i=0; i<digest.length(); i++){
            hashNew = ((int) Math.pow((hashNew + charRU.indexOf(digest.charAt(i))),2))%n;
        }
        System.out.println("Хеш образ сообщения \""+digest+"\" равен: "+hashNew);
        BigInteger s1 = new BigInteger(String.valueOf(hashNew));
        s1 = s1.pow(d);
        s1 = s1.remainder(BigInteger.valueOf(n));

        BigInteger H = s1.pow(e);
        H = H.remainder(BigInteger.valueOf(n));
        System.out.println("H: "+H);
        if (H.intValue() ==hash){
            System.out.println("Электронная подпись совпадает со значением H, ЭП подленная");
        }
        else {
            System.out.println("Электронная подпись подделана");
        }

    }
}
