/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bala Nyan Kyaw
 */
public class memoryPage {
           
               Process process;
               char name;
               char processName;
               int processPage;
               double lastAccessed;
               double runTime;
               int frequency;

               public memoryPage(Process process, int processPage, double lastAccessed, double runTime, int frequency)
               {
                 this.process = process; 
                 this.processPage = processPage; 
                 this.lastAccessed = lastAccessed; 
                 this.runTime = runTime; 
                 this.frequency = frequency;
                 this.name=process.name;
               }
               
               public memoryPage(char c)
               {
                 this.name = c;
               }
               
               public boolean equals(Object other) {
                    if(this.name == ((memoryPage) other).name)
                            return true;
                    else
                            return false;
                    }
}
