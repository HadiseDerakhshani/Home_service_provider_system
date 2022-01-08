package data.user;


import data.serviceSystem.Service;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuperBuilder
@NoArgsConstructor
@Data
@Entity
public class Expert extends User {

    private byte[] image;
    private int score;
    private double credit;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "expertList", fetch = FetchType.EAGER)
    private List<Service> serviceList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Expert expert = (Expert) o;
        return score == expert.score && Double.compare(expert.credit, credit) == 0 && Arrays.equals(image, expert.image) && Objects.equals(serviceList, expert.serviceList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), score, credit, serviceList);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
