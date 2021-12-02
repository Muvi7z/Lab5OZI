package com.company;

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
        int p=5, q=7;
        int n = p*q;
        int form = (p-1)*(q-1); //ЗАКРЫТЫЙ КЛЮЧ

        int hash =random.nextInt(200);  // (h(i-1) + m(i)**2)%2

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
        int s = (int) Math.pow(hash,d);
        s%=n;
        System.out.println("Электронный ключ: "+s);
        System.out.println("Проверка ЭП..");
        int H = (int) Math.pow(s,e);
        H%=n;
        System.out.println("H: "+H);
        if (H==s){
            System.out.println("Электронный ключ совпадает со значением H, ЭП подленный");
        }
        else {
            System.out.println("Электронный ключ yt совпадает со значением H, ЭП подделан");
        }

    }
}
