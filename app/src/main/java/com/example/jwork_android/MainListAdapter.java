package com.example.jwork_android;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for MainLisAdapter
 *
 * @author Ivan Widjanarko
 * @version 27-05-2021
 */
public class MainListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Recruiter> _listDataHeader;
    private HashMap<Recruiter, ArrayList<Job>> _listDataChild;

    /**
     * Constructor for MainListAdapter
     * @param context Context
     * @param listDataHeader List Of Recruiter
     * @param listChildData List of Job
     */
    public MainListAdapter(Context context, ArrayList<Recruiter> listDataHeader, HashMap<Recruiter, ArrayList<Job>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    /**
     * method for getChild
     * @param groupPosition Group's Position
     * @param childPosititon Child's Position
     * @return list data child
     */
    @Override
    public String getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon).getName();
    }

    /**
     * method for getChildId
     * @param groupPosition Group's Position
     * @param childPosition Child'S Position
     * @return Child Position
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * method for getChildView
     * @param groupPosition Group's Position
     * @param childPosition Child's Position
     * @param isLastChild Last Child
     * @param convertView Convert View
     * @param parent Group View
     * @return convert View
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_job, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    /**
     * method for getChildrenCount
     * @param groupPosition Group's Position
     * @return list data child
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    /**
     * method for getGroup
     * @param groupPosition
     * @return list data header
     */
    @Override
    public String getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition).getName();
    }

    /**
     * method for getGroupCount
     * @return list data header
     */
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    /**
     * method for getGroupId
     * @return Group's Position
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * method for getGroupView
     * @return convert view
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_recruiter, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    /**
     * method for hasStableIds
     * @return false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * method for isChildSelectable
     * @return true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
