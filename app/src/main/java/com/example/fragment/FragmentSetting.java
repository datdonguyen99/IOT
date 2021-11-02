package com.example.fragment;

import static com.example.fragment.Constants.HOME_FRAGMENT_INDEX;
import static com.example.fragment.Constants.SETTING_FRAGMENT_INDEX;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

public class FragmentSetting extends Fragment {
    Button btnBack;
//    TextView txtCaption;
    BarChart mChart;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        groupBarChart();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_setting, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ((MainActivity)getActivity()).selectFragment(HOME_FRAGMENT_INDEX);
                ((MainActivity)getActivity()).processDataFragment(SETTING_FRAGMENT_INDEX, "ABC");
            }
        });

//        txtCaption = view.findViewById(R.id.txtSettingCaptain);
//        String strData = getArguments().getString("fragmentTag");
//        Integer intData = getArguments().getInt("fragmentNumber");

//        private void groupBarChart() {
            mChart = (BarChart) view.findViewById(R.id.bar_chart);
            mChart.setDrawBarShadow(false);
            mChart.getDescription().setEnabled(false);
            mChart.setPinchZoom(false);
            mChart.setDrawGridBackground(true);
            // empty labels so that the names are spread evenly
            String[] labels = {"", "Name1", "Name2", "Name3", "Name4", ""};
            XAxis xAxis = mChart.getXAxis();
            xAxis.setCenterAxisLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(true);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setTextColor(Color.BLACK);
            xAxis.setTextSize(12);
            xAxis.setAxisLineColor(Color.WHITE);
            xAxis.setAxisMinimum(1f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setTextColor(Color.BLACK);
            leftAxis.setTextSize(12);
            leftAxis.setAxisLineColor(Color.WHITE);
            leftAxis.setDrawGridLines(true);
            leftAxis.setGranularity(2);
            leftAxis.setLabelCount(8, true);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

            mChart.getAxisRight().setEnabled(false);
            mChart.getLegend().setEnabled(false);

            float[] valOne = {10, 20, 50, 60};
            float[] valTwo = {30, 40, 100, 80};

            ArrayList<BarEntry> barOne = new ArrayList<>();
            ArrayList<BarEntry> barTwo = new ArrayList<>();

            for (int i = 0; i < valOne.length; i++) {
                barOne.add(new BarEntry(i, valOne[i]));
                barTwo.add(new BarEntry(i, valTwo[i]));
            }

            BarDataSet set1 = new BarDataSet(barOne, "barOne");
            set1.setColor(Color.BLUE);
            BarDataSet set2 = new BarDataSet(barTwo, "barTwo");
            set2.setColor(Color.MAGENTA);

            set1.setHighlightEnabled(false);
            set2.setHighlightEnabled(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);

            BarData data = new BarData(dataSets);
            float groupSpace = 0.4f;
            float barSpace = 0f;
            float barWidth = 0.3f;

            data.setBarWidth(barWidth);
            // so that the entire chart is shown when scrolled from right to left
            xAxis.setAxisMaximum(labels.length - 1.1f);
            mChart.setData(data);
            mChart.setScaleEnabled(false);
            mChart.setVisibleXRangeMaximum(6f);
            mChart.groupBars(1f, groupSpace, barSpace);
            mChart.invalidate();
//      }
        return view;
    }

}
