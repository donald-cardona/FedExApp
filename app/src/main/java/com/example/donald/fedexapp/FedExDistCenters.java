package com.example.donald.fedexapp;

/**
 * Created by Donald on 10/16/2019.
 */

import java.util.ArrayList;
import java.util.Stack;

public class FedExDistCenters {
    private ArrayList<Node> locations;
    private Stack<Node> route;
    private boolean visited[];

    public static final int NUM_DIST_CENTERS = 25;
    public static final String[] CENTER_LOC = new String[]
            {"Los Angeles, CA", "Chino, CA", "Sacramento, CA", "Seattle, WA", "Phoenix, AZ",
                    "Salt Lake City, UT", "Denver, CO", "Kansas City, KS", "Dallas, TX", "Houston, TX",
                    "Minneapolis, MN", "St. Louis, MO", "New Berlin, WI", "Memphis, TN", "Indianapolis, IN",
                    "Detroit, MI", "Atlanta, GA", "Grove City, OH", "Pittsburgh, PA", "Orlando, FL",
                    "Charlotte, NC", "Martinsburg, WV", "Edison, NJ", "Allentown, PA", "Northborough, MA"};
    public static final int[][] DIST_CENTERS = new int[][]
            {{0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Los Angeles, CA
                    {1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Chino, CA
                    {1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Sacramento, CA
                    {1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Seattle, WA
                    {1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Phoenix, AZ
                    {1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Salt Lake City, UT
                    {0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Denver, CO
                    {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Kansas City, KS
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Dallas, TX
                    {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Houston, TX
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},	// Minneapolis, MN
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},	// St. Louis, MO
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},	// New Berlin, WI
                    {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},	// Memphis, TN
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},	// Indianapolis, IN
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},	// Detroit, MI
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},	// Atlanta, GA
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0},	// Grove City, OH
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0},	// Pittsburgh, PA
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},	// Orlando, FL
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0},	// Charlotte, NC
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0},	// Martinsburg, WV
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1},	// Edison, NJ
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1},	// Allentown, PA
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0}};	// Northborough, MA


    public FedExDistCenters() {
        locations = new ArrayList<>();					// Initializing ArrayList of Dist. Centers
        for(int i = 0; i < NUM_DIST_CENTERS; i++) {		// Filling ArrayList with locations of Dist. Centers
            Node node = new Node(CENTER_LOC[i]);
            locations.add(node);
        }
        route = new Stack<>();
        visited = new boolean[NUM_DIST_CENTERS];
        for(int i = 0; i < NUM_DIST_CENTERS; i++) {
            visited[i] = false;
        }
    }

    public Node getNode(int index) {
        return locations.get(index);
    }

    public void addNode(int index, Node node) {
        locations.add(index, node);
    }

    public Node removeNode(int index) {
        return locations.remove(index);
    }

    public int size() {
        return locations.size();
    }

    public ArrayList<Node> getLocations() {
        return locations;
    }

    public void setStack(Stack<Node> route) {
        this.route = route;
    }

    public Stack<Node> getStack() {
        return this.route;
    }

    public Stack<Node> shortestPath(ArrayList<Node> locations, Stack<Node> route, int distance, int startIndex, int endIndex) {
        int minDistance = 50;
        int minIndex = 0;
        Node node;

        // For current vertex, examine unvisited neighbors
        // For current vertex, calculate distance of each neighbor from start vertex
        for(int i = 0; i < NUM_DIST_CENTERS; i++ ) {
            // If the calculated distance of a vertex is less than the known distance, update shortest distance.
            // Update the previous vertex for each update distance
            if(DIST_CENTERS[startIndex][i] == 1 && this.visited[i] == false) {
                if(distance < locations.get(i).getDistance()) {
                    locations.get(i).setDistance(distance);
                    locations.get(i).setPrevNode(locations.get(startIndex));
                }
            }
        }
        // Add current vertex to list of visited vertices
        this.visited[startIndex] = true;

        // Visit the unvisited vertex with the smallest known distance from the start vertex
        for(int j = 0; j < NUM_DIST_CENTERS; j++) {
            if((locations.get(j).getDistance() < minDistance) && this.visited[j] == false) {
                minDistance = locations.get(j).getDistance();
                minIndex = j;
            }
        }
        // Repeat until all vertices are visited (minimum distance will not change)
        if(minDistance != 50)
            shortestPath(locations, route, (minDistance + 1), minIndex, endIndex);

        if(!route.isEmpty())
            route.clear();
        node = locations.get(endIndex);
        while(node != null) {
            route.push(node);
            node = node.getPrevNode();
        }

        return route;
    }
}
