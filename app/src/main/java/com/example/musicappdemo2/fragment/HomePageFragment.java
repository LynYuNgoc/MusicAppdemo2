package com.example.musicappdemo2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicappdemo2.R;
import com.example.musicappdemo2.classes.ImageBanner;
import com.example.musicappdemo2.classes.ImageBannerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private ImageBannerAdapter imageBannerAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        imageBannerAdapter = new ImageBannerAdapter(getContext(), getListImgBanner());
        viewPager.setAdapter(imageBannerAdapter);

        circleIndicator.setViewPager(viewPager);
        imageBannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());


        // Tạo một Handler mới
        final Handler handler = new Handler();
        // Tạo một Runnable để thực hiện việc chuyển đổi ảnh trong ViewPager
        final Runnable update = new Runnable() {
            public void run() {
                // Lấy vị trí của trang hiện tại
                int currentPage = viewPager.getCurrentItem();
                // Kiểm tra xem nếu trang hiện tại không phải là trang cuối cùng, thì chuyển đến trang kế tiếp
                if (currentPage == imageBannerAdapter.getCount() - 1) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                // Chuyển đổi đến trang tiếp theo
                viewPager.setCurrentItem(currentPage, true);
            }
        };

        // Thiết lập lịch trình cho việc chuyển đổi ảnh sau mỗi 3 giây
        handler.postDelayed(update, 3000);

        // Sử dụng addOnPageChangeListener để xác định khi người dùng thực hiện thao tác trượt trang
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {
                // Nếu trạng thái là SCROLL_STATE_IDLE (không cuộn), thì chờ 3 giây rồi chuyển đến trang kế tiếp
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    handler.postDelayed(update, 3000);
                }
            }
        });

        return view;
    }

    private List<ImageBanner> getListImgBanner() {

        List<ImageBanner> list = new ArrayList<>();
        list.add(new ImageBanner(R.drawable.imgbanner_0));
        list.add(new ImageBanner(R.drawable.imgbanner_1));
        list.add(new ImageBanner(R.drawable.imgbanner_2));

        return list;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addCategory();
        addArtist();
        addAlbumHot();
        addChill();

    }

    void addCategory() {
        Fragment childFragment = new ListHomePageCategoryFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutHorizontalCategory, childFragment).commit();
    }
    void addArtist() {
        Fragment childFragment = new ListHomePageArtistFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutHorizontalArtist, childFragment).commit();
    }
    void addAlbumHot() {
        Fragment childFragment = new ListHomePageAlbumHotFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutHorizontalAlbumHot, childFragment).commit();
    }
    void addChill() {
        Fragment childFragment = new ListHomePageChillFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutHorizontalChill, childFragment).commit();
    }
}