/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Lambda;

/**
 *
 * @author Administrator
 */
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.List;
import java.util.ArrayList;
// lambda: Khái niệm khái quát hoá các hàm toán học
// VD: lambda x, f : x -> 6X^2 - 5 (có 1 lambda theo x, hàm f là một hàm biểu thức theo x tính gtrị .....)
// Cú pháp lambda: (x) -> biểu thức tính toán
// Cú pháp lambda còn được gọi là lập trình hàm, funtiobal programming


public interface MyFunc<T,U,V> {
    public abstract V eval (T x, U y);
    
    public static void main(String[] args) {
        //f2: (x,y) -> 5x^2 - 6y + 5
        MyFunc<Double, Double, Double> f = new MyFunc<Double, Double, Double>() {
            @Override
            public Double eval(Double x, Double y) {
                return 5*x*x - 6*y + 5;
            }
        };
        System.out.println(f.eval(1.0, -1.0));
        MyFunc<Double, Double, Double> f2 =(x, y) -> {
            return 5*x*x - 6*y + 5;
        };
        System.out.println(f.eval(2.0, -2.0));
        
        //test BiPredicate: t<= u
        BiPredicate<Integer, Integer> bf = (t,u) -> {
            return t <= u;
        };
        System.out.println(bf.test(5, 7));
        
        //ham tim tri max list
        Function<List<Double>, Double> f3 = (t) -> {
            double result = Double.MIN_VALUE;
            for (Double x : t) {
                if(result < x) result = x;
            }
            return result;
        };
        
        ArrayList<Double> L = new ArrayList<>();
        L.add(1.5) ;
        L.add(8.0);
        L.add(-1.5);
        L.add(10.5);
        double max = f3.apply(L);
        System.out.println(max);
    }
}
