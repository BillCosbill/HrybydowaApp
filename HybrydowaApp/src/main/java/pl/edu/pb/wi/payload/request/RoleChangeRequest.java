package pl.edu.pb.wi.payload.request;

import pl.edu.pb.wi.dao.entity.RoleEnum;

public class RoleChangeRequest {

    private Long id;
    private RoleEnum role;

    public RoleChangeRequest(Long id, String role) {
        this.id = id;
        switch (role) {
            case "ROLE_ADMIN":
                this.role = RoleEnum.ROLE_ADMIN;
                break;
            case "ROLE_USER":
                this.role = RoleEnum.ROLE_USER;
                break;
        }
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
