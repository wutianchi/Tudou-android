package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.CityAdapter;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.City;
import com.bentudou.westwinglife.json.Datas;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.gunlei.app.ui.view.ProgressHUD;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yaoguang on 2016/6/28.
 */
public class ShowCityActivity extends BaseTitleActivity{
    private List<Datas> cityList;
    private List<Datas> townList;
    private List<Datas> countyList;
    private ListView city_list;
    private ListView town_list;
    private ListView county_list;
    private CityAdapter adapter;
    private ProgressHUD progressHUD;
    private CityAdapter city_adapter,townAdapter;
    private Intent intent;
    @Override
    protected void setContentView() {
        setContentView(R.layout.show_city);
    }
    @Override
    protected void initView() {
        super.setTitleText("选择城市");
        city_list = (ListView) findViewById(R.id.city_list);
        town_list= (ListView) findViewById(R.id.town_list);
        county_list= (ListView) findViewById(R.id.county_list);
        progressHUD = ProgressHUD.show(ShowCityActivity.this, "读取中", true, null);
        intent = new Intent();
        initData();
    }
    private void initData() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.gainCity(336,new Callback<City>() {
            @Override
            public void success(City city, Response response) {
                progressHUD.dismiss();
                cityList = city.getData();
//              Log.i("asdf","+++++++++++++++++++++"+cityList.toString());
                city_adapter = new CityAdapter(ShowCityActivity.this, cityList);
                city_list.setAdapter(city_adapter);

            }
            @Override
            public void failure(RetrofitError error) {
//                Log.i("asdf","---------------------------------------"+"失败了");
            }
        });
        city_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//              city_list.setSelector(position);
//                view.setSelected(true);
                city_adapter.changeSelected(position);
                intent.putExtra("city",cityList.get(position).getNameZh());
                intent.putExtra("cityID",cityList.get(position).getInternationalId());
                setResult(-1, intent);
                final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
                potatoService.gainCity(Integer.parseInt(cityList.get(position).getInternationalId()),new Callback<City>() {
                    @Override
                    public void success(City city, Response response) {
                        progressHUD.dismiss();
                        townList = city.getData();
//              Log.i("asdf","+++++++++++++++++++++"+cityList.toString());
                        townAdapter = new CityAdapter(ShowCityActivity.this, townList);
                        town_list.setAdapter(townAdapter);
                    }
                    @Override
                    public void failure(RetrofitError error) {
//               Log.i("asdf","---------------------------------------"+"失败了");
                    }
                });
                if(countyList!=null){
                    countyList.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        town_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                view.setSelected(true);
                townAdapter.changeSelected(position);//刷新
                intent.putExtra("town",townList.get(position).getNameZh());
                intent.putExtra("townID",townList.get(position).getInternationalId());
                setResult(-1,intent);
                final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
                potatoService.gainCity(Integer.parseInt(townList.get(position).getInternationalId()),new Callback<City>() {
                    @Override
                    public void success(City city, Response response) {
                        progressHUD.dismiss();
                        countyList = city.getData();
//                Log.i("asdf","+++++++++++++++++++++"+cityList.toString());
                        adapter = new CityAdapter(ShowCityActivity.this,countyList);
                        county_list.setAdapter(adapter);
                    }
                    @Override
                    public void failure(RetrofitError error) {
//               Log.i("asdf","---------------------------------------"+"失败了");
                    }
                });
            }
        });
        county_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         //选中状态item改变
                adapter.changeSelected(position);//刷新
//                view.setSelected(true);
                intent.putExtra("zipCode",countyList.get(position).getZipCode());
                intent.putExtra("county",countyList.get(position).getNameZh());
                intent.putExtra("countyID",countyList.get(position).getInternationalId());
                Log.i("aa","!!!!!!!!!!"+countyList.get(position).getInternationalId()+countyList.get(position).getNameZh()+countyList.get(position).getZipCode());
                setResult(AddtheaddressActivity.RESULT_OK,intent);
                finish();
     }
     });
}
}