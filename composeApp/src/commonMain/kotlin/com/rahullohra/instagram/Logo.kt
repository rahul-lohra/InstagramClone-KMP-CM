import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.instagram_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun IgLogo(modifier: Modifier = Modifier) {
    Icon(
        painterResource(Res.drawable.instagram_logo), null,
        tint = MaterialTheme.colors.onBackground,
        modifier = modifier
    )
}