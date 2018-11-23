package com.vit.flightticketsapp.ui.ticket.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.vit.flightticketsapp.R;
import com.vit.flightticketsapp.data.model.Ticket;
import com.vit.flightticketsapp.ui.base.BaseViewHolder;
import com.vit.flightticketsapp.ui.ticket.listener.OnClickTicketItemListener;
import com.vit.flightticketsapp.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> mTickets = new ArrayList<>();
    private OnClickTicketItemListener mListener;

    public TicketAdapter(List<Ticket> tickets, OnClickTicketItemListener listener) {
        setList(tickets);
        this.mListener = listener;
    }

    public void replaceData(List<Ticket> tickets) {
        setList(tickets);
        notifyDataSetChanged();
    }

    private void setList(List<Ticket> tickets) {
        mTickets = checkNotNull(tickets);
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ticket_item, viewGroup, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder ticketViewHolder, int i) {
        ticketViewHolder.bindData(mTickets.get(i));
    }

    @Override
    public int getItemCount() {
        return mTickets.size();
    }


    class TicketViewHolder extends BaseViewHolder<Ticket> {

        @BindView(R.id.text_airline_name)
        TextView mTextAirlineName;

        @BindView(R.id.image_logo)
        ImageView mImageLogo;

        @BindView(R.id.text_stops)
        TextView mTextStops;

        @BindView(R.id.text_seats)
        TextView mTextSeats;

        @BindView(R.id.text_departure)
        TextView mTextDeparture;

        @BindView(R.id.text_arrival)
        TextView mTextArrival;

        @BindView(R.id.text_duration)
        TextView mTextDuration;

        @BindView(R.id.text_price)
        TextView mTextPrice;

        @BindView(R.id.spin_loader)
        SpinKitView mSpinLoader;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindData(Ticket ticket) {
            GlideApp.with(itemView.getContext())
                    .load(ticket.getAirline().getLogo())
                    .placeholder(R.drawable.ic_flight)
                    .fitCenter()
                    .circleCrop()
                    .into(mImageLogo);

            mTextAirlineName.setText(ticket.getAirline().getName());
            mTextDeparture.setText(String.format("%s Dep", ticket.getDeparture()));
            mTextArrival.setText(String.format("%s Dest", ticket.getArrival()));
            mTextDuration.setText(String.format("%s, %s", ticket.getFlightNumber(), ticket.getDuration()));
            mTextStops.setText(String.format("%s Stops", ticket.getNumberOfStops()));

            if (!TextUtils.isEmpty(ticket.getInstructions())) {
                mTextDuration.append(String.format(", %s", ticket.getInstructions()));
            }

            if (ticket.getPrice() != null) {
                mTextPrice.setText(String.format("$%.0f", ticket.getPrice().getPrice()));
                mTextSeats.setText(String.format(" Seats", ticket.getPrice().getSeats()));
                mSpinLoader.setVisibility(View.INVISIBLE);
            } else {
                mSpinLoader.setVisibility(View.VISIBLE);
            }

        }

        @OnClick(R.id.layout_root)
        void onClickTicket() {
            mListener.onClickTicketItem(mTickets.get(getAdapterPosition()));
        }
    }
}
