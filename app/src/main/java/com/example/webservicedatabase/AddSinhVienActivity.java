package com.example.webservicedatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddSinhVienActivity extends AppCompatActivity {
    EditText edtHoTen, edtNamSinh, edtDiaChi;
    Button btnThem, btnHuy;

    String urlInsert = "http://192.168.1.5/androidwebservice/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sinh_vien);

        AnhXa();


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edtHoTen.getText().toString().trim();
                String namsinh = edtNamSinh.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();
                if (hoten.equals("") || namsinh.equals("") || diachi.equals("")) {
                    Toast.makeText(AddSinhVienActivity.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    ThemSinhVien(urlInsert);
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ThemSinhVien(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(AddSinhVienActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddSinhVienActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(AddSinhVienActivity.this, "Lỗi thêm", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddSinhVienActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("123", "Lỗi: " + error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("hotenSV", edtHoTen.getText().toString().trim());
                params.put("namsinhSV", edtNamSinh.getText().toString().trim());
                params.put("diachiSV", edtDiaChi.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void AnhXa() {
        btnHuy = findViewById(R.id.buttonHuy);
        btnThem = findViewById(R.id.buttonThem);
        edtHoTen = findViewById(R.id.editTextTen);
        edtNamSinh = findViewById(R.id.editTextNamSinh);
        edtDiaChi = findViewById(R.id.editTextDiaChi);
    }
}