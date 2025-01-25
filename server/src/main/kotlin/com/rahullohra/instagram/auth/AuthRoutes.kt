package com.rahullohra.instagram.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.rahullohra.instagram.TimeUtil
import com.rahullohra.instagram.credentials.UserCredentials
import com.rahullohra.instagram.credentials.UserCredentialsTable
import com.rahullohra.instagram.user.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.util.Date
import java.util.UUID

private const val ACCESS_TOKEN_SECRET = "access-token-secret"
private const val REFRESH_TOKEN_SECRET = "refresh-token-secret"
private const val TOKEN_ISSUER = "instagram-clone"


/**
 * Maintain multiple devices to be logged in with same user
 */
fun Routing.authRoutes() {

    post("/auth/register") {
        val registerRequest = call.receive<RegisterRequest>()
        val username = registerRequest.username
        val password = registerRequest.password
        val hashedPassword = hashPassword(password)
        val currentTime = TimeUtil.getCurrentTime()

        UserCredentials.new {
            this.username = username
            this.password = hashedPassword
            this.createdAt = currentTime
        }

        val user = User.new {
         this.username = username
         this.createdAt = currentTime
        }

        val auth = Auth.new {
            this.user = user
            this.createdAt = currentTime
            this.token = generateAccessToken(user.id.toString())
            this.refreshToken = generateRefreshToken(user.id.toString())
        }
        call.respond(status = HttpStatusCode.OK,AuthResponse(user.id.toString(), auth.token, auth.refreshToken))
    }

    post("/auth/login") {
        val loginRequest = call.receive<LoginRequest>()
        val username = loginRequest.username
        val password = loginRequest.password
        val hashedPassword = hashPassword(password)

        if (authenticateUser(username, hashedPassword)){
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }

    }

    post("/auth/refresh_token") {
        val refreshTokenRequest = call.receive<RefreshTokenRequest>()
        val refreshToken = refreshTokenRequest.refreshToken

        // Verify the refresh token
        val verifier = JWT.require(Algorithm.HMAC256(REFRESH_TOKEN_SECRET))
            .withIssuer(TOKEN_ISSUER)
            .build()

        try {
            val decodedJWT = verifier.verify(refreshToken)
            val userId = decodedJWT.getClaim("userId").asString()

            // Generate a new access token
            val newAccessToken = generateAccessToken(userId)
            val newRefreshToken = generateRefreshToken(userId)

            Auth.new {
                this.token = newAccessToken
                this.refreshToken = refreshToken
                this.createdAt = TimeUtil.getCurrentTime()
                this.user = User.findById(UUID.fromString(userId))!!
            }

            call.respond(HttpStatusCode.OK, RefreshTokenResponse(newAccessToken, newRefreshToken))

            AuthTable.deleteWhere {
                AuthTable.refreshToken eq refreshToken
            }

        } catch (e: Exception) {
            call.respond(HttpStatusCode.Unauthorized, "Invalid refresh token")
        }
    }
}

fun hashPassword(password: String): String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
}


fun generateAccessToken(userId: String): String {
    val expirationTime = 15 * 60 * 1000 // 15 minutes
    return JWT.create()
        .withSubject("Authentication")
        .withIssuer(TOKEN_ISSUER)
        .withAudience("your-audience")
        .withClaim("userId", userId)
        .withExpiresAt(Date(System.currentTimeMillis() + expirationTime))
        .sign(Algorithm.HMAC256(ACCESS_TOKEN_SECRET))
}

fun generateRefreshToken(userId: String): String {
    val expirationTime = 7 * 24 * 60 * 60 * 1000 // 7 days
    return JWT.create()
        .withSubject("Refresh")
        .withIssuer(TOKEN_ISSUER)
        .withAudience("your-audience")
        .withClaim("userId", userId)
        .withExpiresAt(Date(System.currentTimeMillis() + expirationTime))
        .sign(Algorithm.HMAC256(REFRESH_TOKEN_SECRET))
}

fun authenticateUser(username: String, password: String): Boolean {
    return transaction {
        val user = UserCredentials.find { UserCredentialsTable.username eq username }.singleOrNull()
        if (user != null && BCrypt.checkpw(password, user.password)) {
            // Authentication successful
            true
        } else {
            // Authentication failed
            false
        }
    }
}

data class AuthResponse(val userId: String, val accessToken: String, val refreshToken: String)

data class RefreshTokenRequest(val refreshToken: String)
data class RefreshTokenResponse(val accessToken:String, val refreshToken: String)

data class RegisterRequest(val username: String, val password: String)
data class LoginRequest(val username: String, val password: String)
