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
 * Class for Main List Adapter
 *
 * @author Ivan Widjanarko
 * @version 19-06-2021
 */
public class MainListAdapter extends BaseExpandableListAdapter {
    private final Context _context;
    private final ArrayList<Recruiter> _listDataHeader;
    private final HashMap<Recruiter, ArrayList<Job>> _listDataChild;
    private final LayoutInflater mLayoutInflater;

    /**
     * Constructor for MainListAdapter
     * @param context Context
     * @param listDataHeader List Of Recruiters
     * @param listChildData List of Jobs
     */
    public MainListAdapter(Context context, ArrayList<Recruiter> listDataHeader, HashMap<Recruiter, ArrayList<Job>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        mLayoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * method for get child
     * @param groupPosition Group's Position
     * @param childPosition Child's Position
     * @return list data child
     */
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return _listDataChild.get(_listDataHeader.get(groupPosition))
                .get(childPosition).getName();
    }

    /**
     * method for get child's ID
     * @param groupPosition Group's Position
     * @param childPosition Child'S Position
     * @return Child Position
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * method for get child's view
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
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_job, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    /**
     * method for get children's count
     * @param groupPosition Group's Position
     * @return list data child
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    /**
     * method for get group
     * @param groupPosition Group's Position
     * @return list data header
     */
    @Override
    public String getGroup(int groupPosition) {
        return _listDataHeader.get(groupPosition).getName();
    }

    /**
     * method for get group's count
     * @return list data header
     */
    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    /**
     * method for get group's ID
     * @param groupPosition Group's Position
     * @return Group's Position
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * method for get group's view
     * @param groupPosition Group's Position
     * @param isExpanded is expanded
     * @param convertView convert view
     * @param parent parent
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
     * method for has stable ids
     * @return false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * method for is child selectable
     * @param groupPosition Group's Position
     * @param childPosition Child's Position
     * @return true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
