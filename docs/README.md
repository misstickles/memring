# MemRing

## TODO

- Read XML attribute (eg, falseness)
- read XML from assets file
- filter classification
- Show position of calls (H, B, M, etc)
  - based on end type (eg, 'b'?)
- notation user input
  - wrangle all the data!
- bell start positions
  - configure scale
- multi columns
  - option / if > X stage (?)
  - repeat last row in column (first of next lead)
- scale spacingwidth for different stages
- options (accordion?) for settings in BL canvas
  - sliders
- move some of generate method to separate functions (?)
- proper object for injected method (place notation, stage, bob, single)
  - show bob, single
- create list of exceptions for double-hunt methods
  - allow to show 1/2 as hunt bells
  - treble place methods (have a `huntbellPath`)
  - what other type of exceptions, stedman?
- Favourite
- Show bluelines in multiple columns

### Persist data

- To persist a view model when (eg, app is closed due to memory). Use for selected method / line / etc.
  - look into saved state
  - source: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#9
  - link: https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-savedstate

## Best Practices

### DI

- https://developer.android.com/training/dependency-injection/hilt-android (Hilt)
- DI docs
  - source: https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
  - link: https://developer.android.com/training/dependency-injection

## Options

- Blue line colour
- font size

## Existing Examples

[Blueline](https://play.google.com/store/apps/details?id=uk.me.rsw.bl&hl=en&gl=US)
Robert Wallis, also rsw.me.uk

[Methodology](https://play.google.com/store/apps/details?id=uk.co.yorkshiresurprise.methodology&hl=en&gl=US)

[Mobel](https://play.google.com/store/apps/details?id=com.abelsim.mobel&hl=en&gl=US)
From Abel, 6.99

## Branches

### test/attempt1

- decent preview with all blue line working
- android not lining up
- trying to add drawText (errors unless negate x / y)
