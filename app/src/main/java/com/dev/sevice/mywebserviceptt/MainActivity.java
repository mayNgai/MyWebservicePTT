package com.dev.sevice.mywebserviceptt;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.XML;

/**
 * Created by admin on 11/28/2017 AD.
 */

public class MainActivity extends AppCompatActivity{
    private static RecyclerView recycler_view;
    private ArrayAdapter<String> AdapterXML;
    private ArrayList<String> ListitemXML = new ArrayList<String>();
    private Button btn_callservice;
    private TextView txt_date,txt_price_ngv;
    private ImageView img_search;
    private static String URL = "http://www.pttplc.com/webservice/pttinfo.asmx";//" http://www.pttplc.com/pttinfo.asmx ";
    private static String NAMESPACE = "http://www.pttplc.com/ptt_webservice/";
    private static String METHOD_NAME = "GetOilPrice";
    private static String SOAP_ACTION = "http://www.pttplc.com/ptt_webservice/GetOilPrice";
    private SoapPrimitive Results = null;
    private List<TblData> dataList;
    private DateController dateController;
    private static String strDay = "", strMonth = "", strYear = "";
    private AdView mAdView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateController = new DateController();
        txt_date = (TextView)findViewById(R.id.txt_date);
        img_search = (ImageView)findViewById(R.id.img_search);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        dataList = new ArrayList<TblData>();
        String strDateTime = dateController.convertDateFormat4To3(dateController.getSystemTime(MainActivity.this)).replace("-",".");
        strDay = strDateTime.substring(0,2);
        strMonth = strDateTime.substring(3,5);
        strYear = strDateTime.substring(6,10);
        txt_date.setText("ณ วันที่" + " "+ strDateTime);
        GetResponseXML();
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDatePicker(txt_date);
            }
        });
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }

    private void setDatePicker(final TextView editText) {
        //String txtDate = dateController.convertDateFormat2To1(editText.getText().toString());
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        String strDate = (mYear - 1) + "-" + (mMonth + 1) + "-" + mDay;
        long longDate = dateController.dateFormat2Tolong(strDate);
        Log.i("date",String.valueOf(longDate));

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String aa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                if (monthOfYear + 1 < 10 || dayOfMonth < 10) {
                    if (monthOfYear + 1 < 10)
                        aa = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                    if (dayOfMonth < 10)
                        aa = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                    if (monthOfYear + 1 < 10 && dayOfMonth < 10)
                        aa = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                }
                String strDateTime = dateController.convertDateFormat2To1(aa).replace("-",".") + " " + dateController.getSystemTimeOnly(MainActivity.this);
                strDay = strDateTime.substring(0,2);
                strMonth = strDateTime.substring(3,5);
                strYear = strDateTime.substring(6,10);
                editText.setText("ณ วันที่" + " "+ strDateTime);
                GetResponseXML();
            }
        }, mYear, mMonth, mDay);
        //datePickerDialog.getDatePicker().setMinDate(longDate);
        datePickerDialog.show();
    }

    private void GetResponseXML(){
        try {
            Thread s = new Thread() {

                @Override
                public void run() {

                    try {

                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                        request.addProperty("Language", "en");
                        request.addProperty("DD", strDay);
                        request.addProperty("MM", strMonth);
                        request.addProperty("YYYY", strYear);

                        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                        envelope.dotNet = true;
                        envelope.setOutputSoapObject(request);

                        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                        androidHttpTransport.call(SOAP_ACTION, envelope);
                        SoapPrimitive resultRequestSOAP = (SoapPrimitive)envelope.getResponse();
                        Results = resultRequestSOAP;

                    } catch (Exception e) {
                        // TODO: handle exception
                        Results= null;
                    } finally {
                        if(Results == null) {
                            Log.e("WebServiceExample", "Soap object Error");

                        } else {
                            pareser();
                        }
                    }

                }
            };
            s.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pareser(){
        try {
            ListitemXML = new ArrayList<String>();
            dataList = new ArrayList<TblData>();
            String sampleXml = Results.toString();

            if(sampleXml.length()>0){
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(sampleXml)));

                Element element=doc.getDocumentElement();
                element.normalize();

                NodeList nList = doc.getElementsByTagName("DataAccess");
                TblData dataNGV = new TblData();
                for (int i=0; i<nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element2 = (Element) node;
                        if(getValue("PRODUCT", element2).equalsIgnoreCase("NGV")){
                            dataNGV.setProduct(getValue("PRODUCT", element2));
                            dataNGV.setPrice(getValue("PRICE", element2));
                            dataNGV.setGroup("บาท/กิโลกรัม");
                        }else if(!getValue("PRICE", element2).equalsIgnoreCase("")){
                            TblData tblData = new TblData();
                            tblData.setProduct(getValue("PRODUCT", element2));
                            tblData.setPrice(getValue("PRICE", element2));
                            dataNGV.setGroup("บาท/ลิตร");
                            dataList.add(tblData);
                            //ListitemXML.add(getValue("PRODUCT", element2)+ "  " + getValue("PRICE", element2));
                        }
                    }
                }
                dataList.add(dataNGV);
                Listing();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getValue(String tag, Element element) {
        String res = "";
        try {
            NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            res = node.getNodeValue();
        }catch (Exception e){
            e.printStackTrace();
            res = "";
            return res;
        }

        return res;
    }

    public void Listing() {

        Runnable run = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                CustomAdapter adapter = new CustomAdapter(MainActivity.this,dataList);
                recycler_view.setAdapter(adapter);
            }
        };
        this.runOnUiThread(run);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("ต้องการออกจากแอปใช่ไหม?");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });
            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
