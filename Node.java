public class Node implements Comparable<Node>{
    private final String id;
    final double longitude, latitude;
    public Node previous;
    private double distance = Integer.MAX_VALUE;
    public Node(String id, double longitude, double latitude)  {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;

        this.distance = Double.POSITIVE_INFINITY;
        this.previous = null;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public void setPrevious (Node previous) {
        this.previous = previous;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getId() { return id; }
    public boolean equals(Node n) {
        return this.id.equals(n.getId());
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(this.distance, o.distance);
    }

    /** GeeksforGeeks: Haversine Formula
     *  Link to source:
     *  <a href="https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/">...</a>
     */
    public double calculateDistance(Node otherNode) {
        double longitude1 = this.getLatitude();
        double latitude1 = this.getLongitude();

        double longitude2 = otherNode.getLatitude();
        double latitude2 = otherNode.getLongitude();

        double deltaLongitude = longitude1 - longitude2;

        return Math.acos(
                Math.sin((latitude1 * Math.PI / 180.0)) * Math.sin((latitude2 * Math.PI / 180.0)) +
                    Math.cos((latitude1 * Math.PI / 180.0)) * Math.cos((latitude2 * Math.PI / 180.0)) *
                        Math.cos((deltaLongitude * Math.PI / 180.0))) * 180.0 / Math.PI * 60 * 1.1515;
    }
}