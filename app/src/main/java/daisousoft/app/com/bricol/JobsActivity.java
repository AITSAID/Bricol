package daisousoft.app.com.bricol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import daisousoft.app.com.bricol.Support.PlayGifView;

public class JobsActivity extends Activity {

    PlayGifView pGif;
    TextView JobIdNmame,JobDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jobs);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width, (int) (height * 0.5));
        getWindow().setGravity(Gravity.BOTTOM);
        JobIdNmame = (TextView) findViewById(R.id.jobidname);
        JobDescription = (TextView) findViewById(R.id.jobdescription);

        pGif = (PlayGifView) findViewById(R.id.viewGif);
        Intent myIntent = getIntent();
        int jobId =Integer.parseInt(myIntent.getStringExtra("jobid"));
        //Toast.makeText(getApplicationContext(),"MY JOB IS  :" +jobId,Toast.LENGTH_LONG).show();

        JobInformation(jobId);
    }

    void JobInformation(int idjob){
        if(111==idjob){
            JobIdNmame.setText(getResources().getString(R.string.Job1));
            pGif.setImageResource(R.drawable.job1_gif);
            JobDescription.setText("• Install and maintain wiring and lighting systems\n" +
                    "• Inspect electrical components, such as transformers and circuit breakers\n" +
                    "• Identify electrical problems with a variety of testing devices\n" +
                    "• Repair or replace wiring, equipment, or fixtures using hand tools and power tools\n" +
                    "• Follow state and local building regulations based on the National Electric Code\n" +
                    "• Direct and train workers to install, maintain, or repair electrical wiring or equipment");
        }
        if(222==idjob){
            JobIdNmame.setText(getResources().getString(R.string.Job2));
            pGif.setImageResource(R.drawable.job2_gif);
            JobDescription.setText("• Read blueprints and drawings to understand or plan the layout of plumbing, waste disposal and water supply systems\n" +
                    "• Cut, assemble and install pipes and tubes with attention to existing infrastructure (e.g. electrical wiring)\n" +
                    "• Install and maintain water supply systems\n" +
                    "• Locate and repair issues with water supply lines (e.g. leaks)\n" +
                    "• Repair or replace broken drainage lines, clogged drains, faucets etc.\n" +
                    "• Repair domestic appliances (e.g. washing machines) and fixtures (e.g. sinks) etc.\n" +
                    "• Install and maintain gas and liquid heating systems (air-conditioning units, radiators etc.)\n" +
                    "• Install waste disposal and sanitary systems with well-functioning DWV systems");
        }
        if(333==idjob){
            JobIdNmame.setText(getResources().getString(R.string.Job3));
            pGif.setImageResource(R.drawable.job3_gif);
            JobDescription.setText("• Apply paint, vinyl and wallpaper including special papers and fabrics to walls, furniture and structures\n" +
                    "• Examine and maintain painted exterior and interior painted surfaces, trimming and fixtures\n" +
                    "• Prepare surfaces and apply paints, stains, shading stains, and clear finishes\n" +
                    "• Remove previous paint by means of sandblasting, scraping, sanding, hydro-blasting and steam-cleaning\n" +
                    "• Inspect and refurbish wall surfaces by means of the appropriate materials\n" +
                    "• Determine, cut and apply wallpaper or fabric to walls\n" +
                    "• Read blueprints and drawings of the premise for the execution of painting job\n" +
                    "• Order paint supplies and materials\n" +
                    "• Apply wood finishing by suitably preparing surface\n" +
                    "• Cover interior walls and ceilings with wallpaper or fabrics\n" +
                    "• Operate and maintain high pressure low volume spray machines\n" +
                    "• Submit finished work orders to supervisor\n" +
                    "• Provide assistance to semi-skilled or unskilled workers\n" +
                    "• Operate and maintain various power and manual tools\n" +
                    "• Maintain logs of Volatile Organic Compound and spray volume\n" +
                    "• Follow established protection procedures\n" +
                    "• Clean up job site after work and return equipment and tools\n" +
                    "• Maintain a clean work environment");
        }
        if(444==idjob){
            JobIdNmame.setText(getResources().getString(R.string.Job4));
            pGif.setImageResource(R.drawable.job4_gif);
            JobDescription.setText("• Keeps equipment available for use by inspecting and testing vehicles; completing preventive maintenance such as, engine tune-ups, oil changes, tire rotation and changes, wheel balancing, replacing filters.\n" +
                    "• Maintains vehicle functional condition by listening to operator complaints; conducting inspections; repairing engine failures; repairing mechanical and electrical systems malfunctions; replacing parts and components; repairing body damage.\n" +
                    "• Verifies vehicle serviceability by conducting test drives; adjusting controls and systems.\n" +
                    "• Complies with state vehicle requirements by testing engine, safety, and combustion control standards.\n" +
                    "• Maintains vehicle appearance by cleaning, washing, and painting.\n" +
                    "• Maintains vehicle records by recording service and repairs.\n" +
                    "• Keeps shop equipment operating by following operating instructions; troubleshooting breakdowns; maintaining supplies; performing preventive maintenance; calling for repairs.\n" +
                    "• Contains costs by using warranty; evaluating service and parts options.\n" +
                    "• Keeps supplies ready by inventorying stock; placing orders; verifying receipt.\n" +
                    "• Updates job knowledge by participating in educational opportunities; reading technical publications.\n" +
                    "• Accomplishes maintenance and organization mission by completing related results as needed.");
        }

        if(555==idjob){
            JobIdNmame.setText(getResources().getString(R.string.Job5));
            pGif.setImageResource(R.drawable.job5_gif);
            JobDescription.setText("• Follow blueprints and building plans to meet the needs of clients\n" +
                    "• Install structures and fixtures, such as windows and molding\n" +
                    "• Measure, cut, or shape wood, plastic, fiberglass, drywall, and other materials\n" +
                    "• Construct building frameworks, including wall studs, floor joists, and doorframes\n" +
                    "• Help put up, level, and install building framework with the aid of large pulleys and cranes\n" +
                    "• Inspect and replace damaged framework or other structures and fixtures\n" +
                    "• Instruct and direct laborers and other construction trade helpers");
        }

        if(666==idjob){
            JobIdNmame.setText(getResources().getString(R.string.Job6));
            pGif.setImageResource(R.drawable.job6_gif);
            JobDescription.setText("• Clean and tidy all areas to the standard cleanliness within time limits\n" +
                    "• Deliver excellent customer service\n" +
                    "• Create daily job lists and record all serviced rooms\n" +
                    "• Maintain equipment in good condition\n" +
                    "• Report on any shortages, damages or security issues\n" +
                    "• Handle reasonable guests complaints/requests and inform others when required\n" +
                    "• Check stocking levels of all consumables\n" +
                    "• Comply with health and safety regulation and act in line with company policies and licensing laws");
        }
        if(777==idjob){
            JobIdNmame.setText(getResources().getString(R.string.Job7));
            pGif.setImageResource(R.drawable.job7_gif);
            JobDescription.setText("• Present lessons in a comprehensive manner and use visual/audio means to facilitate learning\n" +
                    "• Provide individualized instruction to each student by promoting interactive learning\n" +
                    "• Create and distribute educational content (notes, summaries, assignments etc.)\n" +
                    "• Assess and record students’ progress and provide grades and feedback\n" +
                    "• Maintain a tidy and orderly classroom\n" +
                    "• Collaborate with other teachers, parents and stakeholders and participate in regular meetings\n" +
                    "• Plan and execute educational in-class and outdoor activities and events\n" +
                    "• Observe and understand students’ behavior and psyche and report suspicions of neglect, abuse etc.\n" +
                    "• Develop and enrich professional skills and knowledge by attending seminars, conferences etc.");
        }

    }

    public void closeIntent(View v){
        super.onBackPressed();

    }
}
