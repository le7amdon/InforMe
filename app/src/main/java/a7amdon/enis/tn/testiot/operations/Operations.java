package a7amdon.enis.tn.testiot.operations;


import android.content.Context;
import android.graphics.Path;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import a7amdon.enis.tn.testiot.model.Position;

/**
 * Created by 7amdon on 14/12/2016.
 */

public class Operations {

    private final float SEUIL_Z= 15.5f;
    int nb_successives=0;
    Context context;

public Operations(Context context){
    this.context=context;
}




    public Boolean twoAlertsSuccessives(Position new_position,Position old_position){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date new_date;
        Date old_date;
        try {
             new_date = simpleDateFormat.parse(new_position.getDate());
             old_date = simpleDateFormat.parse(old_position.getDate());
            int seconds = (new_date.getSeconds()-old_date.getSeconds());
            if(seconds<=10){
                Toast.makeText(context,"a9al mel 10 !",Toast.LENGTH_SHORT).show();
                Log.d("IJA", "a9al mel 10 !");
                return true;
            }else{
                Toast.makeText(context,"akthar mel 10 !",Toast.LENGTH_SHORT).show();
                Log.d("IJA", "akthar mel 10 !");
                return  false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(context,"lbara 5laaaas !",Toast.LENGTH_SHORT).show();
        Log.d("IJA", "lbara 5laaaas !");
        return false;
    }

    public Boolean twoAlertsSuccessives(long old){
      long diff= (java.lang.System.currentTimeMillis()-old)/1000;
        if (diff<=5) return  true; else return true;
    }


}
