package hu.unideb.timi15.mybookshelf.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import hu.unideb.timi15.mybookshelf.exception.NoSessionException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FirebaseAuthUtil {

    public String getUserId(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isEmpty()) {
            throw new NoSessionException();
        }

        String idToken = authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : authorizationHeader;

        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken).getUid();
        } catch (FirebaseAuthException e) {
            throw new NoSessionException();
        }
    }

}
