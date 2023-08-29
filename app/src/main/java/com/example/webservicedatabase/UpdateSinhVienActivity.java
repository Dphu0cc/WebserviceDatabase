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

public class UpdateSinhVienActivity extends AppCompatActivity {
    EditText edtHoTen, edtNamSinh, edtDiaChi;
    Button btnUpdate, btnHuy;

    int id = 0;

    String urlUpdate = "http://192.168.1.5/androidwebservice/update.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sinh_vien);

        AnhXa();
        Intent intent = getIntent();
        SinhVien sv = (SinhVien) intent.getSerializableExtra("dataSinhVien");
        id = sv.getId();
        edtHoTen.setText(sv.getHoTen().toString());
        edtNamSinh.setText(sv.getNamSinh() + "");
        edtDiaChi.setText(sv.getDiaChi().toString());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edtHoTen.getText().toString().trim();
                String namsinh = edtNamSinh.getText().toString().trim();
                String diachi = edtDiaChi.getText().toString().trim();
                if (hoten.equals("") || namsinh.equals("") || diachi.equals("")) {
                    Toast.makeText(UpdateSinhVienActivity.this, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    UpdateSinhVien(urlUpdate);
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

    private void UpdateSinhVien(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(UpdateSinhVienActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateSinhVienActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(UpdateSinhVienActivity.this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateSinhVienActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("123", "Lỗi: " + error.toString());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idSV", String.valueOf(id));
                params.put("hotenSV", edtHoTen.getText().toString().trim());
                params.put("namsinhSV", edtNamSinh.getText().toString().trim());
                params.put("diachiSV", edtDiaChi.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        btnHuy = findViewById(R.id.buttonHuyUpdate);
        btnUpdate = findViewById(R.id.buttonUpdate);
        edtHoTen = findViewById(R.id.editTextTenUpdate);
        edtNamSinh = findViewById(R.id.editTextNamSinhUpdate);
        edtDiaChi = findViewById(R.id.editTextDiaChiUpdate);
    }
}