package es.globalhands.dto;

import es.globalhands.model.User;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String country,
        String city
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getPhone(), user.getCountry(), user.getCity()
        );
    }
}
