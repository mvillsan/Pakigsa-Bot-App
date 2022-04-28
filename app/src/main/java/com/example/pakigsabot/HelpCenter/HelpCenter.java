package com.example.pakigsabot.HelpCenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.pakigsabot.EmailMessage.EmailMessageLayout;
import com.example.pakigsabot.NavigationFragments.EstFavoritesFragment;
import com.example.pakigsabot.NavigationFragments.HelpCenterFragment;
import com.example.pakigsabot.NavigationFragments.HomeFragment;
import com.example.pakigsabot.NavigationFragments.NearbyFragment;
import com.example.pakigsabot.NavigationFragments.ReservationsFragment;
import com.example.pakigsabot.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.Toast;

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
        setContentView(R.layout.activity_help_center);

        //References::
        refs();

        //Alert Dialog
        builder = new AlertDialog.Builder(this);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

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
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Topics",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Closed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                //Declaration of variables::
                String sR = "Select and Reserve Establishments", reservations = "Your Reservations", payments = "Payments, Pricing and Refunds", accounts = "Your Account", safety="Safety", pakigsabot="About Pakigsa-Bot";
                String sRd1="Establishment Categories to Reserve", sRd2 = "Selecting Establishments Tips", sRd3="Reservation Confirmation", sRd4="Reservation Limits", sRd5="Reservation for friends and family";
                String rD1="Track a Reservation", rD2="Change a Reservation", rD3 = "Checking Reservation Status", rD4="Reservation Pending Status", rD5="Cancelling a Reservation";
                String pD1="Pricing and Fees per Establishment", pD2="Transaction Fees", pD3="Paying for my Reservations", pD4="Refunds", pD5="Deals, Discounts and Vouchers";
                String aD1="Creating an Account", aD2="Managing Account", aD3="Account Subscription", aD4="Account Loyalty Tier", aD5="Account Security";
                String sD1="Safety Concerns", sD2="Safety Tips and Guidelines", sD3="Reporting issues", sD4="Government Restrictions and Advisories";
                String pD="How it Works";

                //Alert Dialog per item::
                if(sR.equals(listDataHeader.get(groupPosition)) && sRd1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Pakigsa-Bot offers a wide variety of establishments. The categories are the following; " +
                            "\n(1)Restaurants \n(2)Cafes \n(3)Resorts \n(4)Dental Clinic \n(5) Eye Clinic \n(6) Spa/Salon \n(7)Internet Cafe \n(8)Coworking Space")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Establishment Categories to Reserve");
                    alert1.show();
                }else if(sR.equals(listDataHeader.get(groupPosition)) && sRd2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("(1) On the Home Page, you can select any establishment category, and then select a specific establishment; " +
                            "\n(2) You can select the Nearby Feature, and select nearby establishments shown from the map; " +
                            "\n(3) Tap a Specific Establishment and read the corresponding descriptions, check what it offers, review its house rules" +
                            ", policies and feedbacks")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.setTitle("Selecting Establishments Tips");
                    alert1.show();
                }else if(sR.equals(listDataHeader.get(groupPosition)) && sRd3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("After reserving an establishment, you will reserve a response after 24 hours. " +
                            "\n\n-If they decline or don’t respond, you won’t be charged in the next reservation and are free to reserve another establishment. ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Reservation Confirmation");
                    alert1.show();
                }else if(sR.equals(listDataHeader.get(groupPosition)) && sRd4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Unsubscribed Customers will be limited only to reserve upto 2 establishments per day; " +
                            "\n\nFor Subscribed Customers, they can reserve upto 3 or more establishments per day")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Reservation Limits");
                    alert1.show();
                }else if(sR.equals(listDataHeader.get(groupPosition)) && sRd5.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("We’d love to welcome your loved ones into Pakigsa-Bot. But to help maintain transparency and trust throughout our community, " +
                            "you can’t book on their behalf unless you're staying together.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Reservation for friends and family");
                    alert1.show();
                }else if(reservations.equals(listDataHeader.get(groupPosition)) && rD1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Keep track on a reservation by tapping the reservations feature on the home screen. " +
                            "\nIt contains all the reservations including its statuses being made by the customer. " +
                            "\nYou can also see in there the reservation history of all the completed/cancelled reservations.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Track a Reservation");
                    alert1.show();
                }else if(reservations.equals(listDataHeader.get(groupPosition)) && rD2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Whether it's changing the reservation date or time, the number of people going, or any detail, " +
                            "a customer can alter a confirmed reservation by sending a request email to the establishment.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Change a Reservation");
                    alert1.show();
                }else if(reservations.equals(listDataHeader.get(groupPosition)) && rD3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("\n-In every reservation, there's a reservation status that keeps you updated (Confirmed, Cancelled or Rescheduled)," +
                            "then Tap for its details (can be found on the Reservations Feature). " +
                            "\n-At the right side, a confirmed status will appear which means you’re good to go! " +
                            "\nYou’ll receive an (SMS) Notification after the confirmation and a day before the date of reservation." +
                            "\n-Cancelled Status means that you didn't reserve a slot" +
                            "\n-Rescheduled Status means that your reservation date has been rescheduled.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Checking Reservation Status");
                    alert1.show();
                }else if(reservations.equals(listDataHeader.get(groupPosition)) && rD4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("A reservation may be \"Pending\" because the establishment needs to respond to a reservation request. " +
                            "\nAn establishment has 24 hours to accept or decline the request. " +
                            "\nThe status for your reservation will be \"Pending\" until the establishments responds. " +
                            "\nDuring this time, you can also contact the establishment through email to approve your request. ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Reservation Pending Status");
                    alert1.show();
                }else if(reservations.equals(listDataHeader.get(groupPosition)) && rD5.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("Upon cancelling a reservation, one must check first the status of their reservations. " +
                            "\nIf the establishment has already confirmed it, then it’s subject to their cancellation policy (cancellation can be done 3 days before the reservation date " +
                            "\n\nIf it’s still pending, you can cancel, and you won’t be charged for the reservation/transaction fees on your next reservation.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Cancelling a Reservation");
                    alert1.show();
                }else if(payments.equals(listDataHeader.get(groupPosition)) && pD1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))){
                    builder.setMessage("In every establishment, the cost of a certain offer, services or products may vary." +
                            "\n\nIn order to know the specific pricing and fees, you can navigate through the application, " +
                            "by clicking a specific establishment. The pricing and fees will be found on the details.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Pricing and Fees per Establishment");
                    alert1.show();
                }else if(payments.equals(listDataHeader.get(groupPosition)) && pD2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("Pakigsa-Bot App collects a transaction fee for every transaction that will take place on the application. " +
                            "\nThe standard cost of the transaction fee is P100")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Transaction Fees");
                    alert1.show();
                }else if(payments.equals(listDataHeader.get(groupPosition)) && pD3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("Paying and communicating through Airbnb helps ensure that you're protected under all of our " +
                            "Establishment and customer safeguards. \n\nYou can pay the fees after you have inputted all the necessary " +
                            "details needed for the reservation. Moreover, you cannot reserve a slot on a specific establishment" +
                            " if you did not pay for the fees. Hence, you will be prompted to pay before you can proceed to reserve.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Paying for my Reservations");
                    alert1.show();
                }else if(payments.equals(listDataHeader.get(groupPosition)) && pD4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("You cannot request a refund on the Pakigsa-Bot App. This is being stated on the " +
                            "establishment's reservation’s cancellation policy. \n\nIf your reservation has been declined " +
                            "or cancelled, you don't need to pay for the transaction fee on your next reservation.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Refunds");
                    alert1.show();
                }else if(payments.equals(listDataHeader.get(groupPosition)) && pD5.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("Deals, Discounts and Vouchers are not convertible to cash. Hence, discounts and vouchers will only " +
                            "be applicable either in free of transaction fees or acquire premium features. " +
                            "\n\nTo see the premium features, you can navigate through your profile and " +
                            "see the premium subscription details and terms.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Deals, Discounts and Vouchers");
                    alert1.show();
                }else if(accounts.equals(listDataHeader.get(groupPosition)) && aD1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("Signing up  or Creating an account is free. You just have to input your credentials on the Sign Up Page of " +
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
                }else if(accounts.equals(listDataHeader.get(groupPosition)) && aD2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
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
                }else if(accounts.equals(listDataHeader.get(groupPosition)) && aD3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("You can subscribe to the Pakigsa-Bot application anytime. " +
                            "\nYou can upgrade to Premium for just P459 a month. " +
                            "\n\nIt includes the SMS Notification Reminders, Exclusive deals, discounts and vouchers, Priority Pass Badge, Can reserve upto 3 or more establishments" +
                            " per day, Priority Concern Service Badge, and Parking Space Reservation. ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Account Subscription");
                    alert1.show();
                }else if(accounts.equals(listDataHeader.get(groupPosition)) && aD4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("The application allowed providers to loyal users a Loyalty Tier that " +
                            "contained Four tiers: \n\n(1)Classic - less than 10 reservations; \n(2)Silver - 10-24 reservations; " +
                            "\n(3)Gold - 25-44 reservations; and \n(4)Platinum - 44 or more reservations. " +
                            "\nThese tiers came with different discounts and vouchers such as birthday vouchers, rewards, and 100% " +
                            "off of transaction fees. \n\nAs such, these will be provided when customers maintain their reservation status " +
                            "and complete a minimum number of reservations every six months.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Account Loyalty Tier");
                    alert1.show();
                }else if(accounts.equals(listDataHeader.get(groupPosition)) && aD5.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
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
                }else if(safety.equals(listDataHeader.get(groupPosition)) && sD1.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("-If you feel as if you may be in danger or your personal safety is threatened, contact the local authorities immediately." +
                            "\n\n-Your safety is a top priority. If you’re injured or require medical attention during an Experience, get yourself to a safe place and " +
                            "contact local emergency services immediately.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Safety Concerns");
                    alert1.show();
                }else if(safety.equals(listDataHeader.get(groupPosition)) && sD2.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("(1)Prepare or buy a first aid kit. Each establishment should have first aid kits." +
                            "Make sure you know where it is, and you should ask, if you don’t know." +
                            "\n(2) For help, download the First Aid app, offered by the Red Cross and Red Crescent network in each country." +
                            "\n(3) Check – Call – Care process could save a life: " +
                            "\n\nCheck: Identify whether an emergency exists—check the person and the surroundings to see if they’re " +
                            "experiencing real risk or distress." +
                            "\n\nCall: If the emergency seems critical, do not hesitate to call the designated emergency number in your country." +
                            "\n\nCare: Stay with the person, monitoring their vital signals and providing information to the medical team")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Safety Tips and Guidelines");
                    alert1.show();
                }else if(safety.equals(listDataHeader.get(groupPosition)) && sD3.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("To report an in-person incident, or to send a detailed report, contact us. " +
                            "Please provide specific details and identify the person you believe has violated the nondiscrimination policy of the application." +
                            "You can email us. Just go to the Help Center Section and tap the email icon.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Reporting issues");
                    alert1.show();
                }else if(safety.equals(listDataHeader.get(groupPosition)) && sD4.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
                    builder.setMessage("Government responses continue to evolve, so please check back often for updates and rely on your local and national government" +
                            " for the most current information. " +
                            "\n\nAs of April 1, 2022, fully vaccinated foreign nationals will be allowed to enter the Philippines without an Entry Exemption Document (EED) " +
                            "provided they comply with all applicable Philippine immigration requirements. " +
                            "Fully vaccinated travelers are no longer subject to facility-based quarantine upon arrival." +
                            "\n\nCarry/possess an acceptable proof of vaccination")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    //Setting the title manually
                    alert1.setTitle("Government Restrictions and Advisories");
                    alert1.show();
                }else if(pakigsabot.equals(listDataHeader.get(groupPosition)) && pD.equals(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition))) {
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
        listDataHeader.add("Select and Reserve Establishments");
        listDataHeader.add("Your Reservations");
        listDataHeader.add("Payments, Pricing and Refunds");
        listDataHeader.add("Your Account");
        listDataHeader.add("Safety");
        listDataHeader.add("About Pakigsa-Bot");

        // Adding child data to Select and Reserve
        List<String> selectReserve = new ArrayList<String>();
        selectReserve.add("Establishment Categories to Reserve");
        selectReserve.add("Selecting Establishments Tips");
        selectReserve.add("Reservation Confirmation");
        selectReserve.add("Reservation Limits");
        selectReserve.add("Reservation for friends and family");

        // Adding child data to Reservations
        List<String> reservations = new ArrayList<String>();
        reservations.add("Track a Reservation");
        reservations.add("Change a Reservation");
        reservations.add("Checking Reservation Status");
        reservations.add("Reservation Pending Status");
        reservations.add("Cancelling a Reservation");

        // Adding child data to Payments, Price, Refunds
        List<String> ppRefunds = new ArrayList<String>();
        ppRefunds.add("Pricing and Fees per Establishment");
        ppRefunds.add("Transaction Fees");
        ppRefunds.add("Paying for my Reservations");
        ppRefunds.add("Refunds");
        ppRefunds.add("Deals, Discounts and Vouchers");

        // Adding child data to Account
        List<String> account = new ArrayList<String>();
        account.add("Creating an Account");
        account.add("Managing Account");
        account.add("Account Subscription");
        account.add("Account Loyalty Tier");
        account.add("Account Security");

        // Adding child data to Safety
        List<String> safety = new ArrayList<String>();
        safety.add("Safety Concerns");
        safety.add("Safety Tips and Guidelines");
        safety.add("Reporting issues");
        safety.add("Government Restrictions and Advisories");

        // Adding child data to Pakigsabot
        List<String> pakigsabot = new ArrayList<String>();
        pakigsabot.add("How it Works");

        // Header, Child data
        listDataChild.put(listDataHeader.get(0), selectReserve);
        listDataChild.put(listDataHeader.get(1), reservations);
        listDataChild.put(listDataHeader.get(2), ppRefunds);
        listDataChild.put(listDataHeader.get(3), account);
        listDataChild.put(listDataHeader.get(4), safety);
        listDataChild.put(listDataHeader.get(5), pakigsabot);
    }

    private void refs(){
        imgBackBtn = findViewById(R.id.imgBackBtn);
        gmailBtn = findViewById(R.id.gmailBtn);
    }

    private void fragments(){
        setContentView(R.layout.activity_bottom_navigation);
        //Bottom Nav
        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_nav);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_nearby));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_reserve));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_favorites));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_help));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                //Initialize fragment
                Fragment fragment = null;

                //Check condition
                switch (item.getId()){
                    case 1: //When id is 1, initialize nearby fragment
                        fragment = new NearbyFragment();
                        break;

                    case 2: //When id is 2, initialize reservations fragment
                        fragment = new ReservationsFragment();
                        break;

                    case 3: //When id is 3, initialize home fragment
                        fragment = new HomeFragment();
                        break;

                    case 4: //When id is 4, initialize favorites fragment
                        fragment = new EstFavoritesFragment();
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