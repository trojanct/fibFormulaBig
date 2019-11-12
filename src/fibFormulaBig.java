import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class fibFormulaBig
{




    static ThreadMXBean bean = ManagementFactory.getThreadMXBean( );

    static String ResultsFolderPath = "/home/cody/Results/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    static int numberOfTrials = 10000;


    public static void main( String[] args)
    {

        //calling full experiment with text file names, running the three experiments as usual for consistency
        runFullExperiment("FibFormbig-Exp1-ThrowAway.txt");
        runFullExperiment("FibFormbig-Exp2.txt");
        runFullExperiment("FibFormbig-Exp3.txt");

        //My testing code used testing the fullest amount in the command bar
       /* int i;
        long result;
        long x = 94;
        for(i = 0; i < x; i++)
        {
            result = fibonacciFunction(i);
            System.out.println(result);
        }*/
    }
    // modified timing code given to us, changed so that input sizes were reduced and went one at a time
    static void runFullExperiment(String resultsFileName){


        double result = 0;
        long inputsize = 1;
        try {

            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);

        } catch(Exception e) {

            System.out.println("*****!!!!!  Had a problem opening the results file "+ResultsFolderPath+resultsFileName);
            return; // not very foolproof... but we do expect to be able to create/open the file...

        }



        ThreadCpuStopWatch BatchStopwatch = new ThreadCpuStopWatch(); // for timing an entire set of trials
        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial


        resultsWriter.println("#InputSize    AverageTime      Fib(x)returned result      input x"); // # marks a comment in gnuplot data
        resultsWriter.flush();
        //uses the x instead of the input
        for(int i = 0 ; i < 93; i++) {

            long batchElapsedTime = 0;


            System.gc();




            // instead of timing each individual trial, we will time the entire set of trials (for a given input size)
            // and divide by the number of trials -- this reduces the impact of the amount of time it takes to call the
            // stopwatch methods themselves
            BatchStopwatch.start(); // comment this line if timing trials individually


            // run the tirals
            for (long trial = 0; trial < numberOfTrials; trial++)
            {

                //call to the fibonaccifuntion gave more descriptive name than fib
                result = fibonacciFunction(i);



            }

            batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double)numberOfTrials; // calculate the average time per trial in this batch

            // tying to get doubling ratio to work.
            //double doublingRatio = (double) averageTimePerTrialInBatch / (double) prevTimePerTrial;
            // prevTimePerTrial = averageTimePerTrialInBatch;


            /* print data for this size of input average time and fibonacci result*/
            if(i > 0) {
                inputsize = (long) (Math.log(i) / Math.log(2) + 1);
            }
            resultsWriter.printf("%12d  %15.2f  %20f %15d\n",inputsize, averageTimePerTrialInBatch, result,i );
            // might as well make the columns look nices look nice
            resultsWriter.flush();
            System.out.println(" ....done.");

        }

    }

    public static double fibonacciFunction(int x)
    {
        BigDecimal result;


        BigDecimal Gold = new BigDecimal((1 + Math.sqrt(5))/2);
        BigDecimal five = new BigDecimal( Math.sqrt(5));

        result = Gold.pow((x));
        result = result.subtract(Gold.pow(-x, MathContext.DECIMAL64));
        result = result.divide(five, RoundingMode.CEILING);

        //result = (Math.pow(((1 + Math.sqrt(5))/2),x) - Math.pow(((1 + Math.sqrt(5))/2),-x))/ Math.sqrt(5)  ;

        return result.doubleValue();

    }






}
