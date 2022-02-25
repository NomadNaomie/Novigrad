package novigrad.services;

import java.util.ArrayList;

public class Service {

    private String name;
    //private ArrayList<String> userForm;
    //private ArrayList<String> userDoc;

    public String getUserForm() {
        return userForm;
    }

    public void setUserForm(String userForm) {
        this.userForm = userForm;
    }

    public String getUserDoc() {
        return userDoc;
    }

    public void setUserDoc(String userDoc) {
        this.userDoc = userDoc;
    }

    private String userForm;
    private String userDoc;
    public String getCombo(){
        return "Documents: " +userDoc + " Forms:" + userForm;
    }

    /*public Service(String serviceName, ArrayList<String> form, ArrayList<String> doc) {
        name = serviceName;
        userForm = form;
        userDoc = doc;
    }*/
    public void setServiceName (String serviceName) {
        name = serviceName;
    }
    /*
    public void setForm (ArrayList<String> form){
        userForm = form;
    }

    public void setDoc (ArrayList<String> doc) {
        userDoc = doc;
    }*/

    /*public String formattedForm(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : userForm){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        return stringBuilder.toString().substring(0,stringBuilder.length()-1);
    }
    public String formattedDocs(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : userForm){
            stringBuilder.append(s);
            stringBuilder.append(",");
        }
        return stringBuilder.toString().substring(0,stringBuilder.length()-1);
    }*/

    public String getName(){return name;}
}
