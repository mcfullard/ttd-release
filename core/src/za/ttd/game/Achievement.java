package za.ttd.game;

public class Achievement {

    private int id, bound;
    private String metric, condition, description;

    public Achievement(int id, int bound, String metric, String condition, String description) {
        this.id = id;
        this.bound = bound;
        this.metric = metric;
        this.condition = condition;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
