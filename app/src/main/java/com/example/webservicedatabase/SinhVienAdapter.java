package com.example.webservicedatabase;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class SinhVienAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;

    private List<SinhVien> arraySinhVien;

    public SinhVienAdapter(MainActivity context, int layout, List<SinhVien> arraySinhVien) {
        this.context = context;
        this.layout = layout;
        this.arraySinhVien = arraySinhVien;
    }

    @Override
    public int getCount() {
        return arraySinhVien.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        private TextView txtHoTen, txtNamSinh, txtDiaChi;
        private ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.txtHoTen = view.findViewById(R.id.textviewHoTenCustom);
            holder.txtNamSinh = view.findViewById(R.id.textviewNamSinh);
            holder.txtDiaChi = view.findViewById(R.id.textviewDiaChi);
            holder.imgDelete = view.findViewById(R.id.imageViewDeleteCustum);
            holder.imgEdit = view.findViewById(R.id.imageViewEditCustom);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        final SinhVien sv = arraySinhVien.get(i);

        holder.txtHoTen.setText(sv.getHoTen());
        holder.txtNamSinh.setText("Năm sinh: " + sv.getNamSinh());
        holder.txtDiaChi.setText((sv.getDiaChi()));

        // bắt sự kiện xóa và sửa
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, com.example.webservicedatabase.UpdateSinhVienActivity.class);
                intent.putExtra("dataSinhVien", sv);
                context.startActivity(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogXoa(sv.getHoTen().toString().trim(), sv.getId());
            }
        });

        return view;
    }

    private void DialogXoa(String tensv, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa sinh viên "+ tensv +" không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.DeleteSinhVien(id);
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }


}
