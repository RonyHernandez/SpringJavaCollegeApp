/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.progra3;

import com.mycompany.progra3.Dto.Persona;
import com.mycompany.progra3.Dto.Producto;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.MathObservable;

/**
 *
 * @author ronyh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Problema 1 =======");
        problema1();
        System.out.println("Problema 2 =======");
        problema2();
        System.out.println("Problema 3 =======");
        problema3();

    }
    
    public static void problema1(){
        List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("rob", 45));
        personas.add(new Persona("bran", 36));
        personas.add(new Persona("john", 8));
        personas.add(new Persona("ned", 55));
        personas.add(new Persona("rickon", 30));

        Observable resultadoPersona =
                Observable
                        .from(personas.toArray())
                        .map((result)-> {
                            Persona persona = (Persona) result;
                            return persona.getEdad();
                        })
                .reduce(
                        new Func2<Integer, Integer, Integer>() {
                            @Override
                            public Integer call(Integer acumulador, Integer actual) {
                               if(actual < acumulador){
                                   return acumulador;
                               } else {
                                   return actual;
                               }
                            }
                        }
                );


        resultadoPersona.subscribe((edad) -> {
            System.out.println("" +
                    "La Mayor Edad es:" + edad);
        });

    }
    
    public static void problema2(){
        
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("ps4", 300));
        productos.add(new Producto("gamecube", 300));
        productos.add(new Producto("external disk", 200));
        productos.add(new Producto("laptop", 800));
        productos.add(new Producto("vr", 230));
        
        Observable productoObservable =
                Observable
                        .from(productos.toArray())
                        .map((result) -> {
                            Producto producto = (Producto) result;
                            return producto.getPrecio();
                        })
                        .reduce(
                                new Func2<Integer, Integer, Integer>() {
                                    @Override
                                    public Integer call(Integer acumulador, Integer actual) {
                                        return acumulador + actual;
                                    }
                                }
                        );
        
        Observable maxPriceObservable =
                Observable
                        .from(productos.toArray())
                        .map((result) -> {
                            Producto producto = (Producto) result;
                            return producto.getPrecio();
                        })
                        .reduce(
                                new Func2<Integer, Integer, Integer>() {
                                    @Override
                                    public Integer call(Integer acumulador, Integer actual) {
                                        if(acumulador < actual){
                                            return actual;
                                        }else{
                                            return acumulador;
                                        }
                                    }
                                }
                        );
        
        maxPriceObservable.subscribe((precioMaximo) -> {
            System.out.println("" +
                    "El mayor precio es:" + precioMaximo);
        });
        
        productoObservable.subscribe((sumatoria) -> {
            System.out.println("" +
                    "La suma de los precios es:" + sumatoria);
            
            double prom = (int)sumatoria / productos.size();
            System.out.println("" +
                    "El promedio de los precios es:" + prom  );
        });


    }
    
    public static void problema3(){
     Integer[] numeros = {2, 5, 6, 8, 10, 35, 2, 10};

      Observable<Integer> numerosObservable = Observable.from(numeros);

        MathObservable
                .from(numerosObservable)
                .averageInteger(numerosObservable)
                .subscribe((promedio) -> {
                    System.out.println("PROMEDIO:" + promedio);
                });
        
        MathObservable
                .from(numerosObservable)
                .sumInteger(numerosObservable)
                .subscribe((sumatoria) -> {
                    System.out.println("SUMATORIA:" + sumatoria);
                });
 
     
      Observable numberObservable =
                Observable
                        .from(numeros)
                        .filter(new Func1<Integer, Boolean>() {
                            @Override
                            public Boolean call(Integer numero) {
                              return( numero >= 10 );
                            }
                        });
       numberObservable.subscribe((filter) -> {
            System.out.println("" +
                    "el array filtrado es:" + filter);              
       });
    }
}
  