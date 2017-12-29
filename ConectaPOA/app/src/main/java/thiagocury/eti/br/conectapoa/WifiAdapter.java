package thiagocury.eti.br.conectapoa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WifiAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Wifi> wf;
    private static ClickListener clickListener;

    public WifiAdapter(Context context, ArrayList<Wifi> wf) {
        this.context = context;
        this.wf = wf;
    }

    public void setWf(ArrayList<Wifi> wf) {
        this.wf = wf;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.linha_wifi,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder hold = (ViewHolder) holder;
        Wifi w = wf.get(position);

        hold.tvEmpresa.setText(w.getEmpresa());
        hold.tvWifi.setText(w.getNomeRede());
        hold.ivSite.setImageResource(R.drawable.ic_site);
        hold.ivEndereco.setImageResource(R.drawable.ic_endereco);

        if(w.getSite().isEmpty()) {
            hold.tvSite.setText(context.getResources().getString(R.string.site_inexistente));
        }else{
            hold.tvSite.setText(w.getSite());
        }

        if(w.getEndereco().isEmpty()) {
            hold.tvEndereco.setText(context.getResources().getString(R.string.endereco_inexistente));
        }else{
            hold.tvEndereco.setText(w.getEndereco());
        }

        if(w.getSetor().equals("Poder Público")){
            hold.ivWifi.setImageResource(R.drawable.ic_wifi_livre);
        }else if(w.getSetor().equals("Rede Privada")){
            hold.ivWifi.setImageResource(R.drawable.ic_wifi_cadeado);
        }
    }

    @Override
    public int getItemCount() {
        return wf.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final TextView tvWifi;
        private final ImageView ivWifi;
        private final TextView tvSite;
        private final ImageView ivSite;
        private final TextView tvEndereco;
        private final ImageView ivEndereco;
        private final TextView tvEmpresa;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tvWifi = itemView.findViewById(R.id.lw_tv_wifi);
            ivWifi = itemView.findViewById(R.id.lw_iv_wifi);
            tvSite = itemView.findViewById(R.id.lw_tv_site);
            ivSite = itemView.findViewById(R.id.lw_iv_site);
            tvEndereco = itemView.findViewById(R.id.lw_tv_endereco);
            ivEndereco = itemView.findViewById(R.id.lw_iv_endereco);
            tvEmpresa = itemView.findViewById(R.id.lw_tv_empresa);
        }

        @Override
        public void onClick (View v){
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick (View v){
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return true;
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        WifiAdapter.clickListener = clickListener;
    }
}
