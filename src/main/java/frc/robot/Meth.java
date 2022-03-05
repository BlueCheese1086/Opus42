package frc.robot;

/*
                                                                                                                                               ██      ██                                               
                                                                                                                                               ██      ██                                              
                                                                                                                                               ██████████                                                                                              
                                                                                                                                               ██      ██                                               
                                                                                                                                               ██      ██                                               
                                                                                                                                                                                                        
                                                                                                                                                                                                        
                                                                                                                                                █▄     ██                                               
                                                                                                                                               ████    ██                                             
                                                                                                                                               ██ ██▄  ██                                               
                                  █████▄                                                 ▄█████                                                ██  ███ ██                                               
                               ██▀█▀  █████                                           █████  ▀█▀███                                            ██   █████                                               
                          ███▀█           █▀███                                   ███▀█╛          █▀███                                  ▌███╛ ██     ███  ███▄                                         
                      ▄█████        ▀███     ██▀███                           ███▀█                  ██▀███                          ┌█████╕    ─      ─    ─█████─                                     
                  ╓█████╤             █████      █████▌                   ▌█████                         █████                    ███▀█▀                        ▀█▀███        ▌██                       
               ███▀█▀                     █████▄    ═█████═           ╒█████═                               ─█████▄           ███▀█                                  █▀██   ████████  ██      █▌        
            ██▀██                             █████▄    ▐█▀███     ███▀█▄                                       ┌█████    ███▀█                                            ██     ██  ██      █▌        
        █████                                    ┌█████╒     █▀███▀█                                                ═████▀█                                               ██          █████████▌        
        ██    ┌                                      ▀█▀███    ██                                                      ██                                                 ███     ███ ██      █▌ ██▀███ 
        ██   █▌                                                ██                                                      ██                                                   ┌█████▄   ██      ██   ▀███▄
        ██   █▌                                                ██                                                      ██                                                                        ██████═
        ██   █▌                                                ██                                                      ██                                                                           █╕  
        ██   █▌                                                ██                                                      ██                                                                               
        ██   █▌                                                ██                                                      ██                                                                               
        ██   █▌                                                ██                                                      ██                                                                               
        ██   █▌                                                ██                                                      ██                                                                               
        ██   █▌                                                ██                                                     █████▄   ██      ██                                                               
        ██   █▌                                           ██   ██                                                   █████ ███  █▌      ██                                                               
        ███┌                                      ┌█████╛     ███                                                  ██          ██████████  ▄█                                                           
          █████                                ███▀█▀     ███▀█▀                                                   ███     ██▀ █▌      ██ █▀███╕                                                        
             ▐█▀███                        ███▀█      ███▀█                                                         █▀█████▀█  █▌      ██   ███                                                         
                  █▀███                ███▀█      ███▀█                                                                ╔╩╝                █▄  ██                                                        
                     ██▀███        ┌██▀██     ▌█████                                                                                      ████▀                                                         
                         █████      █     ╗█████─                                                                                                                                                       
                             █████▄    █████─                                                                                                                                                           
                                 ███████╗                                                                                                                                                               
*/

//Math but im not changing file name
//IT'S BACK
public class Meth {
    
    //Brain damaged rate implementation
    //https://www.desmos.com/calculator/xnjxq7rowq
    private static double rates(double d, double f, double g, double x) {
        double h = (x * (Math.pow(x, 5)*g+x*(1-g)));
        double j;
        if (x < 0) { 
            x = Math.abs(x);
            j = -((d*x) + ((f-d)*h));
        } else {
            j = (d*x) + ((f-d)*h);
        }
        j = j/1000;
        return j;
    }

    /**
     * @param x Input value to perform preset rate calculation on
     * @return Returns the rate calculation
     */
    public static double getRate(double x) {
        double d = 20;
        double f = 1000;
        double g = 0.4;
        return rates(d, f, g, x);
    }

    /**
     * @param d Input to perform rates and deadzone on
     * @return Returns the deadzone and rates
     */
    public static double doMagik(double d) {
        double deadzone = .05;
        if (Math.abs(d) >= deadzone) {
            return getRate(d);
        }
        return 0;
    }

    /**
     * @param d Input to perform turn rates and turn deadzone on
     * @return Returns rates and deadzone
     */
    public static double doTurnMagik(double d) {
        double deadzone = .01;
        if (Math.abs(d) >= deadzone) {
            return rates(250.0, 700, .28, d);
        }
        return 0;
    }

    /**
     * @param d Input to perform deadzone on
     * @param dead What the actual deadzone is
     * @return Returns deadzone with given inputs
     */
    public static double deadzone(double d, double dead) {
        if (Math.abs(d) >= dead) {
            return getRate(d);
        }
        return 0.0;
    }

}