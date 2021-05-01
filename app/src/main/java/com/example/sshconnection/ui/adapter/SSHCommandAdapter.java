package com.example.sshconnection.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sshconnection.R;
import com.example.sshconnection.ui.object.SSHCommandObject;
import java.util.ArrayList;

public class SSHCommandAdapter extends RecyclerView.Adapter<SSHCommandAdapter.ViewHolder> {

  private Context context;
  private ArrayList<SSHCommandObject> SSHCommandObjectList;
  private ItemClickListener mClickListener;

  public SSHCommandAdapter(Context context, ArrayList<SSHCommandObject> SSHCommandObjectList,
      ItemClickListener mClickListener) {
    this.context = context;
    this.SSHCommandObjectList = SSHCommandObjectList;
    this.mClickListener = mClickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_command_list, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    SSHCommandObject SSHCommand = SSHCommandObjectList.get(position);

    if (SSHCommand.getStatus() == 1) {

      holder.tvCommandBtn.setText(SSHCommand.getName());

      holder.cardCommand.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mClickListener.onItemClick(position, SSHCommand);
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return SSHCommandObjectList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    CardView cardCommand;
    AppCompatTextView tvCommandBtn;

    ViewHolder(View itemView) {
      super(itemView);
      cardCommand = itemView.findViewById(R.id.cardCommand);
      tvCommandBtn = itemView.findViewById(R.id.tvCommandBtn);
    }
  }

  public void addData(ArrayList<SSHCommandObject> list) {
    this.SSHCommandObjectList.clear();
    this.SSHCommandObjectList = list;
    notifyDataSetChanged();
  }

  public interface ItemClickListener {
    void onItemClick(int position, SSHCommandObject sshCommand);
  }
}
