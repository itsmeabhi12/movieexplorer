export default function CircleButton({
  onClick,
  icon,
  online = false,
}: {
  onClick?: () => void;
  icon: string;
  online?: boolean;
}) {
  return (
    <button
      className="relative w-10 h-10 bg-red-200 text-white rounded-full flex items-center justify-center"
      onClick={() => onClick?.()}
      aria-live="polite"
    >
      {icon}
      {online && (
        <span
          className="absolute -top-1 -right-1 w-3 h-3 bg-green-500 rounded-full ring-2 ring-white"
          aria-label="Online"
        />
      )}
    </button>
  );
}
