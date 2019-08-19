package iot.empiaurhouse.papyruz.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import iot.empiaurhouse.papyruz.BR;
import iot.empiaurhouse.papyruz.R;

public class LoginFields extends BaseObservable {

    private String name;
    private String email;

    public ObservableField<Integer> nameError = new ObservableField<>();
    public ObservableField<Integer> emailError = new ObservableField<>();


    public String getName(){

        return name;
    }


    public void setName(String name){
        this.name = name;
        notifyPropertyChanged(BR.valid);

    }



    public String getEmail(){

        return email;
    }



    public void setEmail(String email){
        this.email = email;
        notifyPropertyChanged(BR.valid);

    }




    @Bindable
    public  boolean isValid(){
        boolean valid = isNameValid(false);
        valid = isEmailValid(false) && valid;
        return valid;


    }


    public boolean isEmailValid(boolean setMessage){
        //e@domain.c checker
        if (email != null && email.length() > 5){
            int indexOfAt = email.indexOf("@");
            int indexOfDot = email.lastIndexOf(".");
            if (indexOfAt > 0 && indexOfDot > indexOfAt && indexOfDot < email.length() -1){
                emailError.set(null);
                return true;
            }else {
                if (setMessage)
                    emailError.set(R.string.EmailError);
                return false;
            }

        }
        if(setMessage)
            emailError.set(R.string.EmailError);

        return false;
    }




    public boolean isNameValid(boolean setMessage){
        if (name != null && name.length() > 4 ){
            nameError.set(null);
            return true;

        }else {

            if (setMessage)
                nameError.set(R.string.NameError);
            return false;

        }


    }



}
