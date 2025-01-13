import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.instagram_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun IgLogo() {
    Icon(
        painterResource(Res.drawable.instagram_logo), null,
        tint = MaterialTheme.colors.onBackground
    )
}