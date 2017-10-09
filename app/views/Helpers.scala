package views

import org.joda.time.{ DateTime, Period }
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder

object Helpers {

  def timeElapsed(timePosted: DateTime) = {
    val now = new DateTime()
    val period = new Period(timePosted, now)

    val formatter: PeriodFormatter = {
      if (period.getYears() != 0)
        new PeriodFormatterBuilder().appendYears().appendSuffix(" years ago\n").printZeroNever().toFormatter()
      else if (period.getMonths() != 0)
        new PeriodFormatterBuilder().appendMonths().appendSuffix(" months ago\n").printZeroNever().toFormatter()
      else if (period.getWeeks() != 0)
        new PeriodFormatterBuilder().appendWeeks().appendSuffix(" weeks ago\n").printZeroNever().toFormatter()
      else if (period.getDays() != 0)
        new PeriodFormatterBuilder().appendDays().appendSuffix(" days ago\n").printZeroNever().toFormatter()
      else if (period.getHours() != 0)
        new PeriodFormatterBuilder().appendHours().appendSuffix(" hours ago\n").printZeroNever().toFormatter()
      else if (period.getMinutes() != 0)
        new PeriodFormatterBuilder().appendMinutes().appendSuffix(" minutes ago\n").printZeroNever().toFormatter()
      else
        new PeriodFormatterBuilder().appendSeconds().appendSuffix(" seconds ago\n").printZeroNever().toFormatter()
    }

    if (formatter.print(period) == "")
      "posted now"
    else
      formatter.print(period)
  }
}

