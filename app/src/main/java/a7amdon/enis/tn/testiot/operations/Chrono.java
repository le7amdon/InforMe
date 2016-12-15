package a7amdon.enis.tn.testiot.operations;

/**
 * Created by 7amdon on 14/12/2016.
 */

public class Chrono {

    private long tempsDepart=0;
    private long tempsFin=0;
    private long pauseDepart=0;
    private long pauseFin=0;
    private long duree=0;
    Boolean stoped=false;
    Boolean launched=false;
    long last;


    public void start()
    {/*
        tempsDepart=System.currentTimeMillis();
        tempsFin=0;
        pauseDepart=0;
        pauseFin=0;
        duree=0;*/
        duree = java.lang.System.currentTimeMillis() ;
        stoped=false;
        launched = true;

    }

    public void pause()
    {
       /* if(tempsDepart==0) {return;}
        pauseDepart=System.currentTimeMillis();*/
        long chrono2 = java.lang.System.currentTimeMillis() ;
        long temps = chrono2 - duree ;
    }
    public  long getLast(){
        return last;
    }
    public void setLast(long a){
        this.last=last;
    }

    public void resume()
    {
        if(tempsDepart==0) {return;}
        if(pauseDepart==0) {return;}
        pauseFin=System.currentTimeMillis();
        tempsDepart=tempsDepart+pauseFin-pauseDepart;
        tempsFin=0;
        pauseDepart=0;
        pauseFin=0;
        duree=0;
    }

    public void stop()
    {
        /*if(tempsDepart==0) {return;}
        tempsFin=System.currentTimeMillis();
        duree=(tempsFin-tempsDepart) - (pauseFin-pauseDepart);
        tempsDepart=0;
        tempsFin=0;
        pauseDepart=0;
        pauseFin=0;*/
        stoped=true;
        launched=false;
    }
    public Boolean getStoped()
    {
       return stoped;
    }
    public Boolean getLaunched()
    {
        return launched;
    }

    public long getDureeSec()
    {
        return (java.lang.System.currentTimeMillis()-duree)/1000;
    }

    public long getDureeMs()
    {
        return duree;
    }

    public String getDureeTxt()
    {
        return timeToHMS(getDureeSec());
    }

    public static String timeToHMS(long tempsS) {

        // IN : (long) temps en secondes
        // OUT : (String) temps au format texte : "1 h 26 min 3 s"

        int h = (int) (tempsS / 3600);
        int m = (int) ((tempsS % 3600) / 60);
        int s = (int) (tempsS % 60);

        String r="";

        if(h>0) {r+=h+" h ";}
        if(m>0) {r+=m+" min ";}
        if(s>0) {r+=s+" s";}
        if(h<=0 && m<=0 && s<=0) {r="0 s";}

        return r;
    }

} // class Chrono