package mainClient.java;

import java.io.IOException;
import java.util.Hashtable;

public class TestClass {
    interface Shape {

        //неявно public, static и final
        public String LABLE="Shape";

        //методы интерфейса неявно abstract и public
        void draw();

        double getArea();
    }


    class Circle implements Shape {

        private double radius;

        public Circle(double r){
            this.radius = r;
        }

        @Override
        public void draw() {
            System.out.println("Рисуем Круг");
        }

        @Override
        public double getArea(){
            // Вычисляем площадь
            return Math.PI*this.radius*this.radius;
        }

        public double getRadius(){
            return this.radius;
        }
    }

    class Rectangle implements Shape {

        private double width;
        private double height;

        public Rectangle(double w, double h){
            this.width=w;
            this.height=h;
        }
        @Override
        public void draw() {
            System.out.println("Рисуем прямоугольник");
        }

        @Override
        public double getArea() {
            return this.height*this.width;
        }

    }
    Shape c = new Circle(1);
    double n = ((Circle)c).getRadius();
}