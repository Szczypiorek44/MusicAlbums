# Music Albums app

[Link to video](https://youtu.be/oVi1BADNSqo)

This application displays music albums. The app consists of 2 main screens:<br />

- list screen
- details screen App also shows dialog to allow user to retry network call.

I've decided to not use Compose and Realm. I've given myself a week to do this task and I've decided to stick to the
frameworks in which I feel the strongest. I had very limited time during last week due to the change of apartment, so I
didn't want to take a risk that I would not fit inside timeframe that I set myself. Ok enough with the excuses :)

Ingredients:<br />

- Clean Architecture<br />
- MVVM<br />
- LiveData<br />
- ReactiveX<br />
- Koin dependency injection<br />
- Room Database<br />

To introduce module separation I've decided to
use [Clean Architecture](https://www.geeksforgeeks.org/what-is-clean-architecture-in-android/) and divide it into gradle
modules (data, domain, presentation)

I'm proud of:<br />

- CollapsingToolbar works very nice
- Logic inside AlbumRepositoryImpl. I've spent some time thinking how to keep as simple and clean as possible without
  doing some manual value emit which would reduce the readability of the code. Very happy with the results
- There was a problem with scaling details fragment after rotating the screen. Had an idea to create separate layout
  file for horizontal orientation but I didn't want to go this was as it would mostly duplicate the code. I've managed
  to code nice workaround which is added in commit "Handle details screen rotation"

Could be improved:<br />

- Toolbar and status bar are not transparent like on the designs. I wasn't sure if it was a requirement or maybe the
  designs were based on iOS (and maybe on iOS it's common practice). The navigation bar also wasn't visible on the
  design and I assumed this is not a requirement. Instead of those I've to spend time on implementing things thanks to
  which I believe I'll gain more in the eyes of a person who will review this, such as unit tests
- On the design the height of collapsed toolbar was 42dp. So why the hell in my case it's 41dp? Let me explain :) So
  when I've set this parameter to 42dp the toolbar didn't work properly on devices with shorter screens - it wouldn't
  collapse. This is probably due to expanded-collapsed height ratio in this ready-to-use layout and fixing it would
  require to implement something custom which would take not so small amount of time. So to not waste time on
  implementing something that results would be almost imperceptible I've came up easy fix - decrease height a little
  bit :)
- Back arrow at details screen is not perfectly centered. This is due to the fact that it wasn't properly centered in
  prepared svg file. I've decided to waste time on fixing it because mostly in such cases graphic designing takes care
  of such issues

Other notes:<br />

- I've implemented screen rotation handing and some simple unit tests as in my understanding production-ready app is an
  app which does that
- Adding parameter position inside Album model is intentional. This is due to the fact that SQL does not guarantee the
  order of the list
- I'm not using DataBinding on purpose. I think I can say that I know this tool very well, I had a time that I've used
  it everywhere, but after some time I don't like it so much anymore. Mainly because debugging it is such a pain. Just
  my opinion
- I'm dropping a table before inserting new albums to the database because I assume that freshly downloaded albums might
  be different from the ones that were stored last time
