package com.thinkersol.API.models.Admin.AdminManage;
/**
 * The following is licensed under the GNU General Public License v3.0 as found in the LICENSE.md file
 * @author Safwaan Taher
 * @version 1.1
 * @since 8/9/2022
 * <p>
 * Abstract:
 * DashboardItem holds info about a particular plan status
 * that will be displayed to the admin on the dashboard screen
 * </p>
 */
public class DashboardItem {
    /**
     * Private member variables for
     * status: the status that this item will cover
     * completed: the number of plans that correspond to this status
     * total: the total number of plans
     */
    private String status;
    private int completed, total;

    /**
     * constructor: Takes all private properties and assigns them to fields within this Object
     * @author Safwaan Taher
     * @post all fields will be assigned within this object
     */
    public DashboardItem(String status, int completed, int total) {
        this.status = status;
        this.completed = completed;
        this.total = total;
    }

    // default constructor
    public DashboardItem() {
    }

    /*
     * Getters and Setters
     */

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    // toString for debugging
    @Override
    public String toString() {
        return "DashboardItem{" +
                "status='" + status + '\'' +
                ", completed=" + completed +
                ", total=" + total +
                '}';
    }
}
