use List::MoreUtils qw(indexes);

func explode_corner($street, $i) {
    $street->[$i-1]++;
    $street->[$i] -= 2;
    $street->[$i+1]++;
}

my $num_cases = <>;
for my $case (1 .. $num_cases) {
    my $num_corners = <>;
    my ($offset, $first_value) = split ' ', <>;
    $offset *= -1;
    my @street = ($first_value);
    for (1 .. $num_corners - 1) {
        my ($index, $value) = split ' ', <>;
        $street[$index + $offset] = $value;
    }
    @street = map { defined $_ ? $_ : 0 } @street;
    @street = ((0) x 200, @street); # Pad street with zeros on the left
    my $count = 0;
    while (my @indexes = indexes { $_ > 1 } @street) {
        $count += @indexes;
        foreach my $i (@indexes) { explode_corner(\@street, $i) }
    }
    say "Case #$case: $count";
}
