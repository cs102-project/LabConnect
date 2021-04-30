import java.util.Arrays;
import java.util.Locale;

import shapes.*;

/**
 * A tester class for objects inside the shapes package
 * 
 * @author Berkan Şahin
 * @version 28.02.2021
 */
public class ShapeTest {

    /**
     * Finds the shape with the largest area in given array
     * 
     * @param shapes The shape array
     * @return the shape with the largest area
     */
    public static Shape2D findLargestArea(Shape2D[] shapes) {
        Shape2D maxAreaShape = null; // To make java happy
        double maxArea = -1;

        for (Shape2D shape : shapes) {
            if (shape.calculateArea() > maxArea) {
                maxArea = shape.calculateArea();
                maxAreaShape = shape;
            }
        }

        return maxAreaShape;
    }

    /**
     * Finds the shape with the longest perimeter in given array
     * 
     * @param shapes The shape array
     * @return the shape with the longest perimeter
     */
    public static Shape2D findLongestPerimeter(Shape2D[] shapes) {
        Shape2D maxPerimeterShape = null; // To make java happy
        double maxPerimeter = -1;

        for (Shape2D shape : shapes) {
            if (shape.calculatePerimeter() > maxPerimeter) {
                maxPerimeter = shape.calculatePerimeter();
                maxPerimeterShape = shape;
            }
        }

        return maxPerimeterShape;
    }

    public static void main(String[] args) {

        // Variables
        Square square;
        Square square2;
        Square square3;
        Square square4;
        Rectangle rectangle;
        Circle circle;
        Shape2D[] shapes;
        Shape2D maxAreaShape;
        Shape2D maxPerimeterShape;
        String maxAreaShapeType;
        String maxPerimeterShapeType;
        double distance;

        // Program Code

        // Initialize shapes
        rectangle = new Rectangle(2, 3, 15, 8);
        circle = new Circle(13, 15, 7);
        square = new Square(-2, -5, 5);
        square2 = new Square(-2, -5, 5);
        square3 = new Square(-2, -5, 4);
        square4 = null;

        shapes = new Shape2D[3];
        shapes[0] = rectangle;
        shapes[1] = circle;
        shapes[2] = square;

        System.out.println(rectangle);
        System.out.println(circle);
        System.out.printf("sq: %s%n", square);
        System.out.printf("sq2: %s%n", square2);
        System.out.printf("sq3: %s%n", square3);
        System.out.printf("sq4: %s%n", square4);

        // Demonstrate equals()
        System.out.printf("square.equals( square2 ) is %b%n", square.equals(square2));
        System.out.printf("square.equals( square3 ) is %b%n", square.equals(square3));
        System.out.printf("square.equals( square4 ) is %b%n", square.equals(square4));
        System.out.printf("square.equals( circle ) is %b%n", square.equals(circle));

        // Print the array
        System.out.printf("The array is:%s%n", Arrays.toString(shapes));

        // Determine the shapes w/ largest area and longest perimeter
        maxAreaShape = findLargestArea(shapes);
        maxPerimeterShape = findLongestPerimeter(shapes);

        if (maxAreaShape instanceof Circle) {
            maxAreaShapeType = "Circle";
        } else if (maxAreaShape instanceof Rectangle) {
            maxAreaShapeType = "Rectangle";
        } else if (maxAreaShape instanceof Square) {
            maxAreaShapeType = "Square";
        } else {
            maxAreaShapeType = "Unknown";
        }

        if (maxPerimeterShape instanceof Circle) {
            maxPerimeterShapeType = "Circle";
        } else if (maxPerimeterShape instanceof Rectangle) {
            maxPerimeterShapeType = "Rectangle";
        } else if (maxPerimeterShape instanceof Square) {
            maxPerimeterShapeType = "Square";
        } else {
            maxPerimeterShapeType = "Unknown";
        }

        System.out.printf("%s has largest area%n", maxAreaShapeType);
        System.out.printf("%s has longest perimeter%n", maxPerimeterShapeType);

        // Distance between shapes
        for (int i = 0; i < shapes.length - 1; i++) {
            for (int j = i + 1; j < shapes.length; j++) {
                distance = shapes[i].calculateDistance(shapes[j]);
                System.out.printf(Locale.US, "Distance between shape %d and shape %d is %f%n", i + 1, j + 1, distance);
            }
        }

    }

}
