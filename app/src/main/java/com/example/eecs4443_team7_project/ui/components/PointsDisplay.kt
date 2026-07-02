package com.example.eecs4443_team7_project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eecs4443_team7_project.R

@Composable
fun PointsDisplay(
    points: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = colorResource(R.color.cream).copy(alpha = 0.7f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_paw),
            contentDescription = stringResource(R.string.points),
            modifier = Modifier.size(20.dp),
            tint = colorResource(R.color.black)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = points.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = colorResource(R.color.black)
        )
    }
}

@Preview
@Composable
fun PointsDisplayPreview() {
    PointsDisplay(points = 100)
}

