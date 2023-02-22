
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

public class program{
    static void Main(string[] args){
        Console.WriteLine("Main Method Started");
        someMethod();
        Console.WriteLine("Main Method Ended");
    }

    public static void someMethod(){
        Console.WriteLine("Some Method Started");
        Thread.sleep(Timespan.FromSeconds(10));
        Console.WriteLine("\n");
        Console.WriteLine("Some Method Ended");
}
}