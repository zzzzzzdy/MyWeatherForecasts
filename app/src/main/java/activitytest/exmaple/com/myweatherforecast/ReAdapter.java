package activitytest.exmaple.com.myweatherforecast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReAdapter extends RecyclerView.Adapter<ReAdapter.ReViewHolder> {
    private List<Data> datas = new ArrayList<Data>();
    private Context mcontext;

    public ReAdapter(List<Data> datas, Context mcontext) {
        this.datas = datas;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ReAdapter.ReViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item,viewGroup,false);
        return new ReViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReAdapter.ReViewHolder reViewHolder, int i) {
        reViewHolder.title1.setText("日期： "+datas.get(i).getTitle1());
        reViewHolder.title2.setText("最"+datas.get(i).getTitle2());
        reViewHolder.title3.setText("风力： "+datas.get(i).getTitle3());
        reViewHolder.title4.setText("最"+datas.get(i).getTitle4());
        reViewHolder.title5.setText("风向： "+datas.get(i).getTitle5());
        reViewHolder.title6.setText(datas.get(i).getTitle6());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ReViewHolder extends RecyclerView.ViewHolder {
        TextView title1;
        TextView title2;
        TextView title3;
        TextView title4;
        TextView title5;
        TextView title6;
        public ReViewHolder(@NonNull View itemView) {
            super(itemView);
            title1=itemView.findViewById(R.id.tv1);
            title2=itemView.findViewById(R.id.tv2);
            title3=itemView.findViewById(R.id.tv3);
            title4=itemView.findViewById(R.id.tv4);
            title5=itemView.findViewById(R.id.tv5);
            title6=itemView.findViewById(R.id.tv6);
        }
    }
}
