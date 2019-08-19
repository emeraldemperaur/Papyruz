package iot.empiaurhouse.papyruz.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.EditText;

import iot.empiaurhouse.papyruz.model.LoginFields;

public class SignInViewModel extends ViewModel {


    private LoginFields login;
    private View.OnFocusChangeListener onFocusName;
    private View.OnFocusChangeListener onFocusEmail;
    private MutableLiveData<LoginFields> continueButtonClick = new MutableLiveData<>();


    public void init(){
        login = new LoginFields();
        onFocusEmail = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {

                EditText editText = (EditText) view;
                if (editText.getText().length() > 0 && !focused){
                    login.isEmailValid(true);
                }

            }
        };

        onFocusName = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {

                EditText editText = (EditText) view;
                if (editText.getText().length() > 0 && !focused){
                    login.isNameValid(true);
                }

            }
        };


    }



    public LoginFields getLogin(){
        return login;
    }


    public View.OnFocusChangeListener getEmailOnFocusChangeListener(){
        return onFocusEmail;
    }


    public View.OnFocusChangeListener getNameOnFocusChangeListener(){
        return onFocusName;
    }



    public void onButtonClick(){

        if (login.isValid()){
            continueButtonClick.setValue(login);


        }

    }


    public MutableLiveData<LoginFields> getContinueButtonClick(){
        return continueButtonClick;
    }


    @BindingAdapter("error")
    public static void setError(EditText editText, Object strOrResId){
        if (strOrResId instanceof Integer){
            editText.setError(editText.getContext().getString((Integer) strOrResId));
        } else {

            editText.setError((String) strOrResId);

        }

    }


    @BindingAdapter("onFocus")
    public static void bindFocusChange(EditText editText, View.OnFocusChangeListener onFocusChangeListener){
        if (editText.getOnFocusChangeListener() == null){
            editText.setOnFocusChangeListener(onFocusChangeListener);
        }

    }



}
