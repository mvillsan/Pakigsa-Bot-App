package com.example.pakigsabot.HelpCenterBO;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pakigsabot.EmailMessageBO.EmailMessageLayout;
import com.example.pakigsabot.NavigationFragmentsBO.HelpCenterFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HistoryFragment;
import com.example.pakigsabot.NavigationFragmentsBO.HomeFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ReservationsFragment;
import com.example.pakigsabot.NavigationFragmentsBO.ServicesFragment;
import com.example.pakigsabot.R;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpCenter extends AppCompatActivity {

    //Initialize Variables
    ImageView imgBackBtn,gmailBtn;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    AlertDialog.Builder builder;
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center_bo);

        //References::
        refs();

        //Alert Dialog
        builder = new AlertDialog.Builder(this);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableLV);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        imgBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragments();
            }
        });

        gmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email();
            }
        });

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Topics",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collapsed listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Closed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                //Declaration of variables::
                String setUpEst = "Setup Establishment", reserveManage = "Reservation Management", promoDealsManage = "Promo and Deals Management", estAccount = "Your Account", safety = "Safety", pakigsabot= "About Pakigsa-Bot";
                String setUpEstData1 = "SetUp Establishment Location", setUpEstData2 = "SetUp Menu Items/Services/Facilities", setUpEstData3 = "SetUp Promos and Deals", setUpEstData4 = "SetUp Rules", setUpEstData5 = "SetUp Cancellation Policy";
                String reserveManageData1 = "View Reservations", reserveManageData2 = "Accept/Confirm Reservation", reserveManageData3 = "Cancel Reservation", reserveManageData4 = "Reservation History", reserveManageData5= "Delete Reservation";
                String promoDealsManageData1 = "View Promo and Deals", promoDealsManageData2= "Add Promo and Deals", promoDealsManageData3= "Edit Promo and Deals", promoDealsManageData4= "Delete Promo and Deals";
                String estAccountData1 = "Creating an Account", estAccountData2 = "Managing Account", estAccountData3 = "Account Subscription", estAccountData4 = "Account Security";
                String safetyData = "Reporting issues";
                String pakigsabotData ="How it Works";

                //Alert Dialog per item::
                if(setUpEst.equals(listDataHeader.get(groupPosition)) && setUpEstData1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Tap the second button at the bottom navigation. You will be redirected to the Setting-Up Establishment screen. Afterwards, " +
                            "just tap the Proceed button. Then tap the SetUp Location button to set the location of your establishment.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("SetUp Menu Items/Services/Facilities");
                    alert1.show();
                }else if(setUpEst.equals(listDataHeader.get(groupPosition)) && setUpEstData2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Tap the second button at the bottom navigation. You will be redirected to the Setting-Up Establishment screen. Afterwards, " +
                            "just tap the Proceed button. Then tap the Menu Items/Services/Facilities button to setup your establishment's Menu Items/Services/Facilities.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.setTitle("SetUp Menu Items/Services/Facilities");
                    alert1.show();
                }else if(setUpEst.equals(listDataHeader.get(groupPosition)) && setUpEstData3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Tap the second button at the bottom navigation. You will be redirected to the Setting-Up Establishment screen. Afterwards, " +
                            "just tap the Proceed button. Then tap the Promos and Deals button to setup your establishment's Promos and Deals.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("SetUp Promos and Deals");
                    alert1.show();
                }else if(setUpEst.equals(listDataHeader.get(groupPosition)) && setUpEstData4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Tap the second button at the bottom navigation. You will be redirected to the Setting-Up Establishment screen. Afterwards, " +
                            "just tap the Proceed button. Then tap the Rules button to setup your establishment's rules.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("SetUp Rules");
                    alert1.show();
                }else if(setUpEst.equals(listDataHeader.get(groupPosition)) && setUpEstData5.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                builder.setMessage("Tap the second button at the bottom navigation. You will be redirected to the Setting-Up Establishment screen. Afterwards, " +
                        "just tap the Proceed button. Then tap the Cancellation Policy button to setup your establishment's cancellation policy.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                AlertDialog alert1 = builder.create();
                //Setting the title manually
                alert1.setTitle("SetUp Cancellation Policy");
                alert1.show();
            }else if(reserveManage.equals(listDataHeader.get(groupPosition)) && reserveManageData1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Tap the first button at the bottom navigation. You will be redirected to the Reservations screen. Afterwards, " +
                            "just tap the View Reservations button.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("View Reservations");
                    alert1.show();
                }else if(reserveManage.equals(listDataHeader.get(groupPosition)) && reserveManageData2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("If you are already in the View Reservations screen, you will see that each reservation has icons beside them." +
                            "Just tap the (i) icon so you can see the details of the reservation. Button to Confirm the reservation is there.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Accept/Confirm Reservation");
                    alert1.show();
                }else if(reserveManage.equals(listDataHeader.get(groupPosition)) && reserveManageData3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("If you are already in the View Reservations screen, you will see that each reservation has icons beside them." +
                            "Just tap the (i) icon so you can see the details of the reservation. Button to Cancel the reservation is there." +
                            "Upon tapping the Cancel button, you have to input the reason as to why the reservation has been cancelled, then tap Submit.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Cancel Reservation");
                    alert1.show();
                }else if(reserveManage.equals(listDataHeader.get(groupPosition)) && reserveManageData4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Tap the fourth button at the bottom navigation. You will be redirected to the Reservation History screen. Afterwards," +
                                    "just tap the View Reservation History button. To view reservations both Completed and Cancelled.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Reservation History");
                    alert1.show();
                }else if(reserveManage.equals(listDataHeader.get(groupPosition)) && reserveManageData5.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("If you are already in the Reservation History screen, you will see that each reservation has icons beside them." +
                            " Just tap the delete icon so you can delete the reservation from the Reservation History.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Delete Reservation");
                    alert1.show();
                }else if(promoDealsManage.equals(listDataHeader.get(groupPosition)) && promoDealsManageData1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Tap the Promos and Deals button in the SetUp Establishment screen to view your establishment's Promos and Deals.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("View Promo and Deals");
                    alert1.show();
                }else if(promoDealsManage.equals(listDataHeader.get(groupPosition)) && promoDealsManageData2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("(1) Tap the Promos and Deals button in the SetUp Establishment screen." +
                            "\n(2) Tap the (+) to add promos and deals." +
                            "\n(3) Input the necessary details." +
                            "\n(4) Tap the Save button to save.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Add Promo and Deals");
                    alert1.show();
                }else if(promoDealsManage.equals(listDataHeader.get(groupPosition)) && promoDealsManageData3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("If you are already in the Promos and Deals screen, you will see that each promo and deals have icons beside them." +
                            " Just tap the (i) icon so you can see the details. You can modify the details then tap Update button to save the changes.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Edit Promo and Deals");
                    alert1.show();
                }else if(promoDealsManage.equals(listDataHeader.get(groupPosition)) && promoDealsManageData4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("If you are already in the Promos and Deals screen, you will see that each promo and deals have icons beside them." +
                            " Just tap the delete icon so you can delete the  promo  or deal.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Delete Promo and Deals");
                    alert1.show();
                }else if(estAccount.equals(listDataHeader.get(groupPosition)) && estAccountData1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("Signing up  or Creating an account is easy. You just have to subscribe to the Pakigsa-Bot App which costs Php 100 monthly." +
                            " After paying, you must input your credentials on the Sign Up Page of " +
                            "the Pakigsa-Bot Application. Additionally, before creating an account, you have to accept the Pakigsa-Bot Community Commitment" +
                            " or Agreement.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Creating an Account");
                    alert1.show();
                }else if(estAccount.equals(listDataHeader.get(groupPosition)) && estAccountData2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("You can edit your account details, as well as some information on your public profile, from your Profile section." +
                            "\nYou can also reset your password on the profile section. " +
                            "\n\nIn the event that you have forgotten your password, you just have to click the \"Forgot Password\", afterwhich, you will receive" +
                            " a new auto-generated password in your email. This will be found on the Sign In section.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Managing Account");
                    alert1.show();
                }else if(estAccount.equals(listDataHeader.get(groupPosition)) && estAccountData3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("You can subscribe to the Pakigsa-Bot application anytime. " +
                            "\nYou can upgrade to Premium for just P459 a month. " +
                            "\n\nIt includes added privileges such as Generating Monthly Reservations Report to help you manage and track reservations.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Account Subscription");
                    alert1.show();
                }else if(estAccount.equals(listDataHeader.get(groupPosition)) && estAccountData4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("Here’s how you can help protect your account. \n(1)Start with a solid password: " +
                            " Your account's password should be unique—preferably completely different from any other account, " +
                            "such as your email, social media, or your bank. So, if one account is compromised, " +
                            "it's less likely to impact others. " +
                            "\n(2) Flag suspicious activity: If you think something is off about your account, profile, application activity, flag it for review." +
                            "You can email or contact the administration in the Help Center Section." +
                            "\n(3) Stay up to date: Check for software updates that that protect you from security risks." +
                            "\n(4) Always go through us: Keep all payments and communication on the Pakigsa-Bot application. " +
                            "Don't transfer funds outside Pakigsa-Bot or share your email address before a reservation is accepted. " +
                            "If someone emails you asking you to pay or accept payment offsite, contact Pakigsa-Bot administration right away.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Account Security");
                    alert1.show();
                }else if(safety.equals(listDataHeader.get(groupPosition)) && safetyData.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("In the Help Center screen, you can see an email button. If you tap that, you will be redirected to a screen where you can" +
                            " type you concern/s and directly send an email to the Pakigsa-Bot administrator to report your issue. Afterwards, just wait for a reply.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Reporting Issues");
                    alert1.show();
                }else if(pakigsabot.equals(listDataHeader.get(groupPosition)) && pakigsabotData.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("Pakigsa-Bot App serves as a central platform for the accommodation business industry and its customers. \n\n" +
                            "This application will also serve as a tool to reserve appointments or services in various business establishments. " +
                            "Additionally, this application will have a recommender system that recommends business establishments with excellent " +
                            "ratings and reviews according to the user's preferences. It will help the business owners to promote their services " +
                            "to consumers as the application's primary goal. \n\nThe application will also use a notification system with the use " +
                            "of SMS notifications to send reminders to customers regarding their reservations. " +
                            "\n\nRegardless, with today's technology advances, this app provide online reservations for varied establishments, " +
                            "all in one platform, thereby reducing the consumption of its users' mobile devices' storage and memory space.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("How it Works");
                    alert1.show();
                }
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Setup Establishment");
        listDataHeader.add("Reservation Management");
        listDataHeader.add("Promo and Deals Management");
        listDataHeader.add("Your Account");
        listDataHeader.add("Safety");
        listDataHeader.add("About Pakigsa-Bot");

        // Adding child data to SetUp Establishment
        List<String> setUpEst = new ArrayList<String>();
        setUpEst.add("SetUp Establishment Location");
        setUpEst.add("SetUp Menu Items/Services/Facilities");
        setUpEst.add("SetUp Promos and Deals");
        setUpEst.add("SetUp Rules");
        setUpEst.add("SetUp Cancellation Policy");

        // Adding child data to Reservation Management
        List<String> reservationManagement = new ArrayList<String>();
        reservationManagement.add("View Reservations");
        reservationManagement.add("Accept/Confirm Reservation");
        reservationManagement.add("Cancel Reservation");
        reservationManagement.add("Reservation History");
        reservationManagement.add("Delete Reservation");

        // Adding child data to Promos and Deals Management
        List<String> promoDealsManagement = new ArrayList<String>();
        promoDealsManagement.add("View Promo and Deals");
        promoDealsManagement.add("Add Promo and Deals");
        promoDealsManagement.add("Edit Promo and Deals");
        promoDealsManagement.add("Delete Promo and Deals");

        // Adding child data to Establishment Account
        List<String> establishmentAccount = new ArrayList<String>();
        establishmentAccount.add("Creating an Account");
        establishmentAccount.add("Managing Account");
        establishmentAccount.add("Account Subscription");
        establishmentAccount.add("Account Security");

        // Adding child data to Safety
        List<String> safety = new ArrayList<String>();
        safety.add("Reporting issues");;

        // Adding child data to Pakigsabot
        List<String> pakigsabot = new ArrayList<String>();
        pakigsabot.add("How it Works");

        // Header, Child data
        listDataChild.put(listDataHeader.get(0), setUpEst);
        listDataChild.put(listDataHeader.get(1), reservationManagement);
        listDataChild.put(listDataHeader.get(2), promoDealsManagement);
        listDataChild.put(listDataHeader.get(3), establishmentAccount);
        listDataChild.put(listDataHeader.get(4), safety);
        listDataChild.put(listDataHeader.get(5), pakigsabot);
    }

    private void refs(){
        imgBackBtn = findViewById(R.id.imgBackBtn);
        gmailBtn = findViewById(R.id.gmailBtn);
    }

    private void fragments(){
        setContentView(R.layout.activity_bottom_navigation_bo);
        //Bottom Nav
        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_nav_bo);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_reservations_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_services_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_home_24_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_history_bo));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_help));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;

                //Check condition
                switch (item.getId()){
                    case 1: //When id is 1, initialize nearby fragment
                        fragment = new ReservationsFragment();
                        break;

                    case 2: //When id is 2, initialize reservations fragment
                        fragment = new ServicesFragment();
                        break;

                    case 3: //When id is 3, initialize home fragment
                        fragment = new HomeFragment();
                        break;

                    case 4: //When id is 4, initialize favorites fragment
                        fragment = new HistoryFragment();
                        break;

                    case 5: //When id is 5, initialize help center fragment
                        fragment = new HelpCenterFragment();
                        break;
                }
                //Load fragment
                loadFragment(fragment);
            }
        });

        /*//Set notification count
        bottomNavigation.setCount(3,"10");*/
        //Set help center fragment initially selected
        bottomNavigation.show(5,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        //Replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

    private void email(){
        Intent intent = new Intent(getApplicationContext(), EmailMessageLayout.class);
        startActivity(intent);
    }
}